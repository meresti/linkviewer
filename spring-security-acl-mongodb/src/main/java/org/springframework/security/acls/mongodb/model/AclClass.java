package org.springframework.security.acls.mongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "AclClass")
public class AclClass {
    @Id
    private String id;

    private String className;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(final String className) {
        this.className = className;
    }
}
