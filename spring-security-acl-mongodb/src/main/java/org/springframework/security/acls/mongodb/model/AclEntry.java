package org.springframework.security.acls.mongodb.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "AclEntry")
public class AclEntry {
    @Id
    private String id;

    private String objectIdentityId;
    private String sid;

    private int order;
    private int mask;
    private boolean granting;
    private boolean auditSuccess;
    private boolean auditFailure;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getObjectIdentityId() {
        return objectIdentityId;
    }

    public void setObjectIdentityId(final String objectIdentityId) {
        this.objectIdentityId = objectIdentityId;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(final String sid) {
        this.sid = sid;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(final int order) {
        this.order = order;
    }

    public int getMask() {
        return mask;
    }

    public void setMask(final int mask) {
        this.mask = mask;
    }

    public boolean isGranting() {
        return granting;
    }

    public void setGranting(final boolean granting) {
        this.granting = granting;
    }

    public boolean isAuditSuccess() {
        return auditSuccess;
    }

    public void setAuditSuccess(final boolean auditSuccess) {
        this.auditSuccess = auditSuccess;
    }

    public boolean isAuditFailure() {
        return auditFailure;
    }

    public void setAuditFailure(final boolean auditFailure) {
        this.auditFailure = auditFailure;
    }
}
