package meresti.linkviewer.core.services.impl;

import meresti.linkviewer.core.entities.Link;
import meresti.linkviewer.core.services.LinkService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {
    @Override
    public List<Link> findLinks(long startIndex, long pageSize) {
        final Link link = findById(BigInteger.ONE);
        return Collections.singletonList(link);
    }

    @Override
    public Link findById(BigInteger id) {
        final Link link = new Link();
        link.setId(id);
        link.setUrl("http://some.url");
        link.setTitle("A dummy link");
        link.setDescription("A dummy link about something");
        link.setImageUrl("http://some-image.url");
        return link;
    }
}
