package meresti.linkviewer.rest.resources.asm;

import meresti.linkviewer.core.entities.Link;
import meresti.linkviewer.rest.controllers.LinkController;
import meresti.linkviewer.rest.resources.LinkResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class LinkResourceAsm extends ResourceAssemblerSupport<Link, LinkResource> {
    public LinkResourceAsm() {
        super(LinkController.class, LinkResource.class);
    }

    @Override
    public LinkResource toResource(final Link entity) {
        final LinkResource result = new LinkResource(entity.getUrl(), entity.getTitle(), entity.getDescription(), entity.getImageUrl());
        result.add(linkTo(methodOn(LinkController.class).getLink(entity.getId())).withSelfRel());
        return result;
    }
}
