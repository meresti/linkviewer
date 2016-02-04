/*
 * Copyright (c) 2016. meresti
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

package meresti.linkviewer.core.services.impl;

import meresti.linkviewer.core.entities.ContentRoom;
import meresti.linkviewer.core.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private MutableAclService mutableAclService;

    @Override
    public void addPermission(final ContentRoom room, final Sid sid, final Permission permission) {
        final ObjectIdentity objectIdentity = createObjectIdentityFrom(room);
        addPermission(objectIdentity, sid, permission);
    }

    @Override
    public void addPermission(final ObjectIdentity objectIdentity, final Sid sid, final Permission permission) {
        final MutableAcl mutableAcl = mutableAclService.createAcl(objectIdentity);
        mutableAcl.insertAce(mutableAcl.getEntries().size(), permission, sid, true);
        mutableAclService.updateAcl(mutableAcl);
    }

    @Override
    public void deletePermission(final ContentRoom room, final Sid sid, final Permission permission) {
        final ObjectIdentity objectIdentity = createObjectIdentityFrom(room);
        final MutableAcl acl = (MutableAcl) mutableAclService.readAclById(objectIdentity);

        // Remove all permissions associated with this particular recipient (string equality to KISS)
        final List<AccessControlEntry> entries = acl.getEntries();
        for (int i = 0; i < entries.size(); i++) {
            final AccessControlEntry entry = entries.get(i);
            if (entry.getSid().equals(sid) && entry.getPermission().equals(permission)) {
                acl.deleteAce(i);
            }
        }

        mutableAclService.updateAcl(acl);
    }

    private static ObjectIdentity createObjectIdentityFrom(final ContentRoom contentRoom) {
        return new ObjectIdentityImpl(ContentRoom.class, contentRoom.getId().toString());
    }
}
