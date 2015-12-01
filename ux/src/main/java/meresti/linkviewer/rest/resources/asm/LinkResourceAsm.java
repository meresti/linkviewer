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

import meresti.linkviewer.core.entities.Link;
import meresti.linkviewer.rest.controllers.LinkController;
import meresti.linkviewer.rest.resources.LinkResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class LinkResourceAsm extends ResourceAssemblerSupport<Link, LinkResource> {
    public LinkResourceAsm() {
        super(LinkController.class, LinkResource.class);
    }

    @Override
    public LinkResource toResource(final Link entity) {
        final LinkResource result = new LinkResource(entity.getUrl(), entity.getTitle(), entity.getDescription(), entity.getImageUrl());
//        result.add(linkTo(methodOn(LinkController.class).getLink(entity.getId())).withSelfRel());
        return result;
    }
}
