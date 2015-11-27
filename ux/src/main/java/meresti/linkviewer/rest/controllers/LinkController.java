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
