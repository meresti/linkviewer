/*
 * Copyright (c) 2015. meresti
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package meresti.linkviewer.core.services.impl;

import meresti.linkviewer.core.SpecialContentRooms;
import meresti.linkviewer.core.entities.ContentRoom;
import meresti.linkviewer.core.entities.ContentRoomLink;
import meresti.linkviewer.core.entities.Link;
import meresti.linkviewer.core.exceptions.ObjectNotFoundException;
import meresti.linkviewer.core.repositories.ContentRoomLinkRepository;
import meresti.linkviewer.core.repositories.ContentRoomRepository;
import meresti.linkviewer.core.repositories.LinkRepository;
import meresti.linkviewer.core.services.ContentRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Transactional(readOnly = true)
public class ContentRoomServiceImpl implements ContentRoomService {

    private final AtomicLong counter = new AtomicLong(1L);

    @Autowired
    private ContentRoomRepository contentRoomRepository;

    @Autowired
    private ContentRoomLinkRepository contentRoomLinkRepository;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private MutableAclService mutableAclService;

    @Override
    @Transactional(readOnly = false)
    public ContentRoom createRoom(final ContentRoom room) {

        final ContentRoom result = contentRoomRepository.save(room);

        addPermission(result, new PrincipalSid(SecurityContextHolder.getContext().getAuthentication()), BasePermission.ADMINISTRATION);

        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public List<ContentRoom> createRooms(final List<ContentRoom> rooms) {
        final List<ContentRoom> savedRooms = contentRoomRepository.save(rooms);
        final Sid sid = new PrincipalSid(SecurityContextHolder.getContext().getAuthentication());
        for (final ContentRoom room : savedRooms) {
            addPermission(room, sid, BasePermission.ADMINISTRATION);
        }
        return savedRooms;
    }

    @Override
    @Transactional(readOnly = false)
    public ContentRoom deleteRoom(final BigInteger id) {
        final ContentRoom contentRoom = findById(id);
        contentRoomRepository.delete(contentRoom);

        final ObjectIdentity objectIdentity = createObjectIdentityFrom(contentRoom);
        mutableAclService.deleteAcl(objectIdentity, false);

        return contentRoom;
    }

    private static ObjectIdentity createObjectIdentityFrom(final ContentRoom contentRoom) {
        return new ObjectIdentityImpl(ContentRoom.class, contentRoom.getId().toString());
    }

    @Override
    @Transactional(readOnly = false)
    public ContentRoom deleteRoom(final ContentRoom room) {
        contentRoomRepository.delete(room);
        return room;
    }

    @Override
    public List<ContentRoom> getRooms() {
        return contentRoomRepository.findAll();
    }

    @Override
    public ContentRoom findById(final BigInteger id) {
        final ContentRoom contentRoom = contentRoomRepository.findOne(id);
        if (contentRoom == null) {
            throw new ObjectNotFoundException("Content room :" + id.toString());
        }
        return contentRoom;
    }

    @Override
    @Transactional(readOnly = false)
    public ContentRoomLink addLinkToRoom(final BigInteger roomId, final Link link) {
        final ContentRoom contentRoom = findById(roomId);
        ContentRoomLink contentRoomLink = contentRoomLinkRepository.findByRoomAndLink(contentRoom, link);
        if (contentRoomLink == null) {
            contentRoomLink = contentRoomLinkRepository.save(new ContentRoomLink(null, contentRoom, link));
        }
        return contentRoomLink;
    }

    @Override
    @Transactional(readOnly = false)
    public ContentRoomLink removeLinkFromRoom(final BigInteger roomId, final BigInteger linkId) {
        final ContentRoom contentRoom = findById(roomId);
        final Link link = findLinkById(linkId);
        final ContentRoomLink contentRoomLink = contentRoomLinkRepository.findByRoomAndLink(contentRoom, link);
        if (contentRoomLink != null) {
            contentRoomLinkRepository.delete(contentRoomLink);
        }
        return contentRoomLink;
    }

    @Override
    public List<ContentRoomLink> findLinks(final BigInteger roomId, final long startIndex, final int pageSize) {

        final int page = (int) (startIndex / (long) pageSize);
        final int elementsToSkip = (int) (startIndex % (long) pageSize);

        final Page<ContentRoomLink> contentRoomLinkPage;
        if (SpecialContentRooms.ALL_ROOMS.getId().equals(roomId)) {
            final Sort sort = new Sort(Sort.Direction.DESC, "relevanceRate");
            final Pageable pageRequest = new PageRequest(page, pageSize, sort);
            contentRoomLinkPage = contentRoomLinkRepository.findAll(pageRequest);
        } else {
            final ContentRoom contentRoom = findById(roomId);
            final Pageable pageRequest = new PageRequest(page, pageSize);
            contentRoomLinkPage = contentRoomLinkRepository.findByRoom(contentRoom, pageRequest);
        }

        final List<ContentRoomLink> contentRoomLinks = contentRoomLinkPage.getContent();
        return contentRoomLinks.subList(elementsToSkip, contentRoomLinks.size());
    }

    @Override
    public Link findLinkById(final BigInteger id) {
        final Link link = linkRepository.findOne(id);
        if (link == null) {
            throw new ObjectNotFoundException("Link: " + id.toString());
        }

        return link;
    }


    @Override
    public void addPermission(final ContentRoom room, final Sid sid, final Permission permission) {
        final ObjectIdentity objectIdentity = createObjectIdentityFrom(room);
        final MutableAcl mutableAcl = mutableAclService.createAcl(objectIdentity);
        mutableAcl.insertAce(mutableAcl.getEntries().size(), permission, sid, true);
        mutableAclService.updateAcl(mutableAcl);

    }

    @Override
    public void deletePermission(final ContentRoom room, final Sid sid, final Permission permission) {
        final ObjectIdentity objectIdentity = createObjectIdentityFrom(room);
        final MutableAcl acl = (MutableAcl) mutableAclService.readAclById(objectIdentity);

        // Remove all permissions associated with this particular recipient (string equality to KISS)
        final List<AccessControlEntry> entries = acl.getEntries();
        for (int i = 0; i < entries.size(); i++) {
            final AccessControlEntry entry = entries.get(i);
            if (entry.getSid().equals(sid) && entry.getPermission().equals(permission)) {
                acl.deleteAce(i);
            }
        }

        mutableAclService.updateAcl(acl);
    }
}
