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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @Override
    @Transactional(readOnly = false)
    public ContentRoom createRoom(final ContentRoom room) {

        return contentRoomRepository.save(room);
    }

    @Override
    @Transactional(readOnly = false)
    public ContentRoom deleteRoom(final BigInteger id) {
        final ContentRoom contentRoom = findById(id);
        contentRoomRepository.delete(contentRoom);
        return contentRoom;
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
    public Link addLinkToRoom(final BigInteger roomId, final Link link) {
        final BigInteger linkId = link.getId();
        ContentRoomLink contentRoomLink = contentRoomLinkRepository.findByRoomIdAndLinkId(roomId, linkId);
        if (contentRoomLink == null) {
            contentRoomLink = new ContentRoomLink(null, roomId, linkId);
            contentRoomLinkRepository.save(contentRoomLink);
        }
        return link;
    }

    @Override
    @Transactional(readOnly = false)
    public Link removeLinkFromRoom(final BigInteger roomId, final BigInteger linkId) {
        final Link link = findLinkById(linkId);
        final ContentRoomLink contentRoomLink = contentRoomLinkRepository.findByRoomIdAndLinkId(roomId, linkId);
        if (contentRoomLink != null) {
            contentRoomLinkRepository.delete(contentRoomLink);
        }
        return link;
    }

    @Override
    public List<Link> findLinks(final BigInteger roomId, final int page, final int size) {

        final Page<ContentRoomLink> contentRoomLinkPage = contentRoomLinkRepository.findByRoomId(roomId, new PageRequest(page, size));
        final List<ContentRoomLink> contentRoomLinks = contentRoomLinkPage.getContent();
        final List<BigInteger> linkIds = contentRoomLinks.stream().map(ContentRoomLink::getLinkId).collect(Collectors.toList());
        final Iterable<Link> links = linkRepository.findAll(linkIds);
        return StreamSupport.stream(links.spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Link findLinkById(final BigInteger id) {
        final Link link = linkRepository.findOne(id);
        if (link == null) {
            throw new ObjectNotFoundException("Link: " + id.toString());
        }

        return link;
    }
}
