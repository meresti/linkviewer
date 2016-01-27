package org.springframework.security.acls.mongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "AclSid")
public class AclSid {
    @Id
    private String id;

    private String sid;

    private boolean principal;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(final String sid) {
        this.sid = sid;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(final boolean principal) {
        this.principal = principal;
    }
}
