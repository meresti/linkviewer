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

import meresti.linkviewer.core.entities.ContentRoomLink;
import meresti.linkviewer.rest.controllers.ContentRoomController;
import meresti.linkviewer.rest.resources.ContentRoomLinkResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.math.BigDecimal;

public class ContentRoomLinkResourceAsm extends ResourceAssemblerSupport<ContentRoomLink, ContentRoomLinkResource> {

    private final LinkResourceAsm linkResourceAsm;

    public ContentRoomLinkResourceAsm(final LinkResourceAsm linkResourceAsm) {
        super(ContentRoomController.class, ContentRoomLinkResource.class);
        this.linkResourceAsm = linkResourceAsm;
    }

    @Override
    public ContentRoomLinkResource toResource(final ContentRoomLink entity) {
        final ContentRoomLinkResource resource = new ContentRoomLinkResource();
        resource.setLink(linkResourceAsm.toResource(entity.getLink()));
        resource.setRelevance(entity.getRelevance());
        if (entity.getRelevanceRate() != null) {
            resource.setRelevanceRate(entity.getRelevanceRate().toString());
        }
        return resource;
    }

    public ContentRoomLink fromResource(final ContentRoomLinkResource resource) {
        final ContentRoomLink result = new ContentRoomLink();
        result.setLink(linkResourceAsm.fromResource(resource.getLink()));
        result.setRelevance(resource.getRelevance());
        if (resource.getRelevanceRate() != null) {
            result.setRelevanceRate(new BigDecimal(resource.getRelevanceRate()));
        }
        return result;
    }
}
