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

package meresti.linkviewer.rest.controllers;

import meresti.linkviewer.core.entities.ContentRoom;
import meresti.linkviewer.core.entities.Link;
import meresti.linkviewer.core.exceptions.ObjectAlreadyExists;
import meresti.linkviewer.core.services.ContentRoomService;
import meresti.linkviewer.rest.exceptions.ConflictException;
import meresti.linkviewer.rest.exceptions.NotFoundException;
import meresti.linkviewer.rest.resources.ContentRoomResource;
import meresti.linkviewer.rest.resources.LinkResource;
import meresti.linkviewer.rest.resources.asm.ContentRoomResourceAsm;
import meresti.linkviewer.rest.resources.asm.LinkResourceAsm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class ContentRoomController {

    private final ContentRoomService contentRoomService;

    @Autowired
    public ContentRoomController(final ContentRoomService contentRoomService) {
        this.contentRoomService = contentRoomService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ContentRoomResource>> getRooms() {
        final List<ContentRoom> rooms = contentRoomService.getRooms();
        final List<ContentRoomResource> resources = new ContentRoomResourceAsm().toResources(rooms);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ContentRoomResource> createRoom(@RequestBody final ContentRoomResource sentResource) {
        ResponseEntity<ContentRoomResource> createdResource = null;
        try {
            final ContentRoomResourceAsm resourceAsm = new ContentRoomResourceAsm();
            final ContentRoom sentContentRoom = resourceAsm.fromResource(sentResource);
            final ContentRoom createdContentRoom = contentRoomService.createRoom(sentContentRoom);
            createdResource = new ResponseEntity<>(resourceAsm.toResource(createdContentRoom), HttpStatus.OK);
        } catch (final ObjectAlreadyExists ex) {
            throw new ConflictException(ex);
        }

        return createdResource;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<ContentRoomResource> deleteRoom(@RequestBody final ContentRoomResource sentResource) {
        final ContentRoomResourceAsm resourceAsm = new ContentRoomResourceAsm();
        final ContentRoom sentContentRoom = resourceAsm.fromResource(sentResource);
        final ContentRoom createdContentRoom = contentRoomService.deleteRoom(sentContentRoom);
        return new ResponseEntity<>(resourceAsm.toResource(createdContentRoom), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{roomId}/links/{startIndex}/{pageSize}")
    public ResponseEntity<List<LinkResource>> getLinks(@PathVariable("roomId") final BigInteger roomId,
                                                       @PathVariable("startIndex") final long startIndex,
                                                       @PathVariable("pageSize") final long pageSize) {
        final List<Link> links = contentRoomService.findLinks(roomId, startIndex, pageSize);
        final List<LinkResource> resources = new LinkResourceAsm().toResources(links);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{roomId}/links/{linkId}")
    public LinkResource getLink(@PathVariable("roomId") final BigInteger roomId,
                                @PathVariable("linkId") final BigInteger linkId) {

        final Link link = contentRoomService.findById(linkId);
        if (link == null) {
            throw new NotFoundException();
        }
        final ResourceAssembler<Link, LinkResource> asm = new LinkResourceAsm();
        return asm.toResource(link);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{roomId}/links")
    public LinkResource addLinkToRoom(@PathVariable("roomId") final BigInteger roomId,
                                      @RequestBody final LinkResource sentLinkResource) {

        return sentLinkResource;
    }
}
