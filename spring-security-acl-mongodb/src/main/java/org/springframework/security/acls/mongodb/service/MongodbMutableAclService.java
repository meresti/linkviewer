package org.springframework.security.acls.mongodb.service;

import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.model.*;
import org.springframework.security.acls.mongodb.dao.AclEntryRepository;
import org.springframework.security.acls.mongodb.dao.AclObjectIdentityRepository;
import org.springframework.security.acls.mongodb.dao.AclSidRepository;
import org.springframework.security.acls.mongodb.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

import java.util.List;


public class MongodbMutableAclService extends MongodbAclService implements MutableAclService {

    public MongodbMutableAclService(AclEntryRepository aclEntryRepository,
                                    AclObjectIdentityRepository objectIdentityRepository,
                                    AclSidRepository aclSidRepository,
                                    AclClassService aclClassService,
                                    AclCache aclCache,
                                    AclAuthorizationStrategy aclAuthorizationStrategy,
                                    PermissionFactory permissionFactory,
                                    PermissionGrantingStrategy grantingStrategy) {
        super(aclEntryRepository, objectIdentityRepository, aclSidRepository, aclClassService,
                aclCache, aclAuthorizationStrategy,
                permissionFactory, grantingStrategy);
    }

    @Override
    public MutableAcl createAcl(ObjectIdentity objectIdentity) throws AlreadyExistsException {
        Assert.notNull(objectIdentity, "Object Identity required");

        // Check this object identity hasn't already been persisted
        if (retrieveObjectIdentityPrimaryKey(objectIdentity) != null) {
            throw new AlreadyExistsException("Object identity '" + objectIdentity + "' already exists");
        }

        // Need to retrieve the current principal, in order to know who "owns" this ACL (can be changed later on)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PrincipalSid sid = new PrincipalSid(auth);

        // Create the acl_object_identity row
        createObjectIdentity(objectIdentity, sid);

        // Retrieve the ACL via superclass (ensures cache registration, proper retrieval etc)
        Acl acl = readAclById(objectIdentity);
        Assert.isInstanceOf(MutableAcl.class, acl, "MutableAcl should be been returned");

        return (MutableAcl) acl;
    }

    protected String retrieveObjectIdentityPrimaryKey(ObjectIdentity oid) {
        String objectIdClass = aclClassService.getObjectClassId(oid.getType());
        if (objectIdClass == null) return null;

        QAclObjectIdentity aclObjectIdentity = QAclObjectIdentity.aclObjectIdentity;
        AclObjectIdentity aoi = objectIdentityRepository.findOne(
                aclObjectIdentity.objectIdIdentity.eq((String) oid.getIdentifier())
                        .and(aclObjectIdentity.objectIdClass.eq(objectIdClass))
        );
        if (aoi != null) {
            return aoi.getId();
        }
        return null;
    }

    /**
     * Creates an entry in the acl_object_identity table for the passed ObjectIdentity. The Sid is also
     * necessary, as acl_object_identity has defined the sid column as non-null.
     *
     * @param object to represent an acl_object_identity for
     * @param owner  for the SID column (will be created if there is no acl_sid entry for this particular Sid already)
     */
    protected void createObjectIdentity(ObjectIdentity object, Sid owner) {
        String sidId = createOrRetrieveSidPrimaryKey(owner, true);
        String classId = createOrRetrieveClassPrimaryKey(object.getType(), true);
        AclObjectIdentity objectIdentity = new AclObjectIdentity();
        objectIdentity.setObjectIdClass(classId);
        objectIdentity.setObjectIdIdentity((String) object.getIdentifier());
        objectIdentity.setOwnerId(sidId);
        objectIdentity.setEntriesInheriting(true);
        objectIdentityRepository.save(objectIdentity);
    }

    /**
     * Retrieves the primary key from acl_sid, creating a new row if needed and the allowCreate property is
     * true.
     *
     * @param sid         to find or create
     * @param allowCreate true if creation is permitted if not found
     * @return the primary key or null if not found
     * @throws IllegalArgumentException if the <tt>Sid</tt> is not a recognized implementation.
     */
    protected String createOrRetrieveSidPrimaryKey(Sid sid, boolean allowCreate) {
        Assert.notNull(sid, "Sid required");

        String sidName;
        boolean sidIsPrincipal = true;

        if (sid instanceof PrincipalSid) {
            sidName = ((PrincipalSid) sid).getPrincipal();
        } else if (sid instanceof GrantedAuthoritySid) {
            sidName = ((GrantedAuthoritySid) sid).getGrantedAuthority();
            sidIsPrincipal = false;
        } else {
            throw new IllegalArgumentException("Unsupported implementation of Sid");
        }

        QAclSid aclSid = QAclSid.aclSid;
        List<AclSid> sids = (List<AclSid>) sidRepository.findAll(aclSid.principal.eq(sidIsPrincipal).and(aclSid.sid.eq(sidName)));

        if (!sids.isEmpty()) {
            return sids.get(0).getId();
        }

        if (allowCreate) {
            AclSid newSid = new AclSid();
            newSid.setPrincipal(sidIsPrincipal);
            newSid.setSid(sidName);
            return sidRepository.save(newSid).getId();
        }

        return null;
    }

    /**
     * Retrieves the primary key from {@code acl_class}, creating a new row if needed and the
     * {@code allowCreate} property is {@code true}.
     *
     * @param type        to find or create an entry for (often the fully-qualified class name)
     * @param allowCreate true if creation is permitted if not found
     * @return the primary key or null if not found
     */
    protected String createOrRetrieveClassPrimaryKey(String type, boolean allowCreate) {
        String classId = aclClassService.getObjectClassId(type);
        if (classId != null) {
            return classId;
        }

        if (allowCreate) {
            AclClass aclClass = new AclClass();
            aclClass.setClassName(type);
            aclClass = aclClassService.createAclClass(aclClass);
            return aclClass.getId();
        }

        return null;
    }


    @Override
    public void deleteAcl(ObjectIdentity objectIdentity, boolean deleteChildren) throws ChildrenExistException {
        Assert.notNull(objectIdentity, "Object Identity required");
        Assert.notNull(objectIdentity.getIdentifier(), "Object Identity doesn't provide an identifier");

        List<ObjectIdentity> children = findChildren(objectIdentity);
        if (deleteChildren) {
            if (children != null) {
                for (ObjectIdentity child : children) {
                    deleteAcl(child, true);
                }
            }
        } else {
            // TODO: Could use mongoTemplate.findAndModify to optimize
            QAclObjectIdentity aclObjectIdentity = QAclObjectIdentity.aclObjectIdentity;
            Iterable<AclObjectIdentity> aoiChildren = objectIdentityRepository.findAll(
                    aclObjectIdentity.parentObjectId.eq((String) objectIdentity.getIdentifier()));
            for (AclObjectIdentity aoi : aoiChildren) {
                aoi.setParentObjectId(null);
                objectIdentityRepository.save(aoi);
            }
        }

        String oidPrimaryKey = retrieveObjectIdentityPrimaryKey(objectIdentity);

        // Delete this ACL's ACEs in the acl_entry table
        deleteEntries(oidPrimaryKey);

        // Delete this ACL's acl_object_identity row
        deleteObjectIdentity(oidPrimaryKey);

        // Clear the cache
        aclCache.evictFromCache(objectIdentity);
    }

    private void deleteEntries(String oidPrimaryKey) {
        //TODO: Could use mongoTemplate.findAndRemove to optimize deleting processing in one go
        QAclEntry aclEntry = QAclEntry.aclEntry;
        aclEntryRepository.delete(aclEntryRepository.findAll(aclEntry.objectIdentityId.eq(oidPrimaryKey)));
    }

    private void deleteObjectIdentity(String oidPrimaryKey) {
        objectIdentityRepository.delete(oidPrimaryKey);
    }

    /**
     * This implementation will simply delete all ACEs in the database and recreate them on each invocation of
     * this method. A more comprehensive implementation might use dirty state checking, or more likely use ORM
     * capabilities for create, update and delete operations of {@link MutableAcl}.
     */
    @Override
    public MutableAcl updateAcl(MutableAcl acl) throws NotFoundException {
        Assert.notNull(acl.getId(), "Object Identity doesn't provide an identifier");

        // Delete this ACL's ACEs in the acl_entry table
        deleteEntries(retrieveObjectIdentityPrimaryKey(acl.getObjectIdentity()));

        // Create this ACL's ACEs in the acl_entry table
        createEntries(acl);

        // Change the mutable columns in acl_object_identity
        updateObjectIdentity(acl);

        // Clear the cache, including children
        clearCacheIncludingChildren(acl.getObjectIdentity());

        // Retrieve the ACL via superclass (ensures cache registration, proper retrieval etc)
        return (MutableAcl) super.readAclById(acl.getObjectIdentity());
    }

    protected void createEntries(final MutableAcl acl) {
        if (acl.getEntries() == null || acl.getEntries().size() == 0) return;
        int order = 0;
        for (AccessControlEntry entry_ : acl.getEntries()) {
            Assert.isTrue(entry_ instanceof AccessControlEntryImpl, "Unknown ACE class");
            AccessControlEntryImpl entry = (AccessControlEntryImpl) entry_;
            AclEntry aclEntry = new AclEntry();
            aclEntry.setSid(createOrRetrieveSidPrimaryKey(entry.getSid(), true));
            aclEntry.setOrder(order);
            aclEntry.setObjectIdentityId((String) acl.getId());
            aclEntry.setMask(entry.getPermission().getMask());
            aclEntry.setGranting(entry.isGranting());
            aclEntry.setAuditSuccess(entry.isAuditSuccess());
            aclEntry.setAuditFailure(entry.isAuditFailure());
            aclEntryRepository.save(aclEntry);

            order++;
        }
    }

    /**
     * Updates an existing acl_object_identity row, with new information presented in the passed MutableAcl
     * object. Also will create an acl_sid entry if needed for the Sid that owns the MutableAcl.
     *
     * @param acl to modify (a row must already exist in acl_object_identity)
     * @throws NotFoundException if the ACL could not be found to update.
     */
    protected void updateObjectIdentity(MutableAcl acl) {
        String parentId = null;

        if (acl.getParentAcl() != null) {
            Assert.isInstanceOf(ObjectIdentityImpl.class, acl.getParentAcl().getObjectIdentity(),
                    "Implementation only supports ObjectIdentityImpl");

            ObjectIdentityImpl oii = (ObjectIdentityImpl) acl.getParentAcl().getObjectIdentity();
            parentId = retrieveObjectIdentityPrimaryKey(oii);
        }

        Assert.notNull(acl.getOwner(), "Owner is required in this implementation");

        String ownerSid = createOrRetrieveSidPrimaryKey(acl.getOwner(), true);
        AclObjectIdentity aoi = objectIdentityRepository.findOne((String) acl.getId());
        if (aoi == null) {
            throw new NotFoundException("Unable to locate ACL to update");
        }
        aoi.setParentObjectId(parentId);
        aoi.setOwnerId(ownerSid);
        aoi.setEntriesInheriting(Boolean.valueOf(acl.isEntriesInheriting()));
        objectIdentityRepository.save(aoi);
    }

    private void clearCacheIncludingChildren(ObjectIdentity objectIdentity) {
        Assert.notNull(objectIdentity, "ObjectIdentity required");
        List<ObjectIdentity> children = findChildren(objectIdentity);
        if (children != null) {
            for (ObjectIdentity child : children) {
                clearCacheIncludingChildren(child);
            }
        }
        aclCache.evictFromCache(objectIdentity);
    }
}