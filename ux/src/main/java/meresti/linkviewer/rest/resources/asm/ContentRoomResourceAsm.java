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

package meresti.linkviewer.rest.resources.asm;

import meresti.linkviewer.core.entities.ContentRoom;
import meresti.linkviewer.rest.controllers.ContentRoomController;
import meresti.linkviewer.rest.resources.ContentRoomResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.math.BigInteger;

public class ContentRoomResourceAsm extends ResourceAssemblerSupport<ContentRoom, ContentRoomResource> {

    public ContentRoomResourceAsm() {
        super(ContentRoomController.class, ContentRoomResource.class);
    }

    @Override
    public ContentRoomResource toResource(final ContentRoom entity) {
        final BigInteger id = entity.getId();
        final ContentRoomResource resource = new ContentRoomResource(id != null ? id.toString() : null, entity.getName());
        return resource;
    }

    public ContentRoom fromResource(final ContentRoomResource resource) {
        return new ContentRoom(resource.getRoomId() != null ? new BigInteger(resource.getRoomId()) : null, resource.getName());
    }
}
