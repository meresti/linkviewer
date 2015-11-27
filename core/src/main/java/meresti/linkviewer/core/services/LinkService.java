package meresti.linkviewer.core.services;

import meresti.linkviewer.core.entities.Link;

import java.math.BigInteger;
import java.util.List;

public interface LinkService {
    List<Link> findLinks(long startIndex, long pageSize);

    Link findById(BigInteger id);
}
