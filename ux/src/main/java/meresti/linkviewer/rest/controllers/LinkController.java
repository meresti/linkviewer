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

import meresti.linkviewer.core.entities.Link;
import meresti.linkviewer.core.services.LinkService;
import meresti.linkviewer.rest.exceptions.NotFoundException;
import meresti.linkviewer.rest.resources.LinkResource;
import meresti.linkviewer.rest.resources.asm.LinkResourceAsm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/links")
public class LinkController {

    private LinkService linkService;

    @Autowired
    public LinkController(final LinkService linkService) {
        this.linkService = linkService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{startIndex}/{pageSize}")
    public ResponseEntity<List<LinkResource>> getLinks(@PathVariable("startIndex") final long startIndex, @PathVariable("pageSize") final long pageSize) {
        final List<Link> links = linkService.findLinks(startIndex, pageSize);
        final List<LinkResource> resources = new LinkResourceAsm().toResources(links);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public LinkResource getLink(@PathVariable("id") final BigInteger id) {
        final Link link = linkService.findById(id);
        if (link == null) {
            throw new NotFoundException();
        }
        final LinkResourceAsm asm = new LinkResourceAsm();
        return asm.toResource(link);
    }
}
