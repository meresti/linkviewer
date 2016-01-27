package org.springframework.security.acls.mongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "AclObjectIdentity")
public class AclObjectIdentity {
    @Id
    private String id;

    private String objectIdClass;
    private String objectIdIdentity;

    private String parentObjectId;
    private String ownerId;

    private boolean entriesInheriting;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getObjectIdClass() {
        return objectIdClass;
    }

    public void setObjectIdClass(final String objectIdClass) {
        this.objectIdClass = objectIdClass;
    }

    public String getObjectIdIdentity() {
        return objectIdIdentity;
    }

    public void setObjectIdIdentity(final String objectIdIdentity) {
        this.objectIdIdentity = objectIdIdentity;
    }

    public String getParentObjectId() {
        return parentObjectId;
    }

    public void setParentObjectId(final String parentObjectId) {
        this.parentObjectId = parentObjectId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(final String ownerId) {
        this.ownerId = ownerId;
    }

    public boolean isEntriesInheriting() {
        return entriesInheriting;
    }

    public void setEntriesInheriting(final boolean entriesInheriting) {
        this.entriesInheriting = entriesInheriting;
    }
}
