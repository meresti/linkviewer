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

package meresti.linkviewer.core.services;

import meresti.linkviewer.core.entities.ContentRoom;
import meresti.linkviewer.core.entities.ContentRoomLink;
import meresti.linkviewer.core.entities.Link;
import meresti.linkviewer.core.security.AclDelete;
import meresti.linkviewer.core.security.AclGrantPermission;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import java.math.BigInteger;
import java.util.List;

public interface ContentRoomService {

    @PreAuthorize("isAuthenticated()")
    @AclGrantPermission("admin")
    ContentRoom createRoom(ContentRoom room);

    @PreAuthorize("isAuthenticated()")
    @AclGrantPermission("admin")
    List<ContentRoom> createRooms(List<ContentRoom> rooms);

    @PreAuthorize("hasPermission(#id?.toString(),'meresti.linkviewer.core.entities.ContentRoom', admin)")
    @AclDelete
    ContentRoom deleteRoom(BigInteger id);

    @PreAuthorize("hasPermission(#room?.getId(),'meresti.linkviewer.core.entities.ContentRoom', admin)")
    @AclDelete
    ContentRoom deleteRoom(ContentRoom room);

    @PreAuthorize("isAuthenticated()")
    @PostFilter("hasPermission(filterObject?.getId()?.toString(),'meresti.linkviewer.core.entities.ContentRoom', 'read') or " +
            "hasPermission(filterObject?.getId()?.toString(),'meresti.linkviewer.core.entities.ContentRoom', admin)")
    List<ContentRoom> getRooms();

    @PreAuthorize("hasPermission(#id?.toString(),'meresti.linkviewer.core.entities.ContentRoom', 'read') or hasPermission(#id?.toString(),'meresti.linkviewer.core.entities.ContentRoom', admin)")
    ContentRoom findById(BigInteger id);

    @PreAuthorize("hasPermission(#roomId?.toString(),'meresti.linkviewer.core.entities.ContentRoom', admin)")
    ContentRoomLink addLinkToRoom(BigInteger roomId, Link link);

    @PreAuthorize("hasPermission(#roomId?.toString(),'meresti.linkviewer.core.entities.ContentRoom', admin)")
    ContentRoomLink removeLinkFromRoom(BigInteger roomId, BigInteger linkId);

    @PreAuthorize("hasPermission(#roomId?.toString(),'meresti.linkviewer.core.entities.ContentRoom', 'read') or hasPermission(#roomId?.toString(),'meresti.linkviewer.core.entities.ContentRoom', admin)")
    List<ContentRoomLink> findLinks(BigInteger roomId, long startIndex, int pageSize);

    Link findLinkById(BigInteger id);
}
