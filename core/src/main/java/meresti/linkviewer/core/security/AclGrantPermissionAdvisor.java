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
package meresti.linkviewer.core.security;

import meresti.linkviewer.core.entities.ContentRoom;
import meresti.linkviewer.core.services.ContentRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PermissionFactory;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
@SuppressWarnings("serial")
public class AclGrantPermissionAdvisor extends AbstractAnnotationBasedAdvisor<ContentRoomService, AclGrantPermission, ContentRoom> {

    private final MutableAclService mutableAclService;
    private final PermissionFactory permissionFactory;

    @Autowired
    public AclGrantPermissionAdvisor(final MutableAclService mutableAclService, final PermissionFactory permissionFactory) {
        super(ContentRoomService.class, AclGrantPermission.class, ContentRoom.class);

        this.mutableAclService = mutableAclService;
        this.permissionFactory = permissionFactory;
    }

    @Override
    protected void advice(final ContentRoom returnValue, final AclGrantPermission annotation) {
        final ObjectIdentity objectIdentity = new ObjectIdentityImpl(ContentRoom.class, returnValue.getId().toString());

        final Sid sid = new PrincipalSid(SecurityContextHolder.getContext().getAuthentication());
        final MutableAcl mutableAcl = mutableAclService.createAcl(objectIdentity);
        for (final Permission permission : getPermissions(annotation.value())) {
            mutableAcl.insertAce(mutableAcl.getEntries().size(), permission, sid, true);
        }
        mutableAclService.updateAcl(mutableAcl);
    }

    private Iterable<Permission> getPermissions(final String[] names) {
        return Arrays.stream(names).map(this::getPermission).collect(Collectors.toList());
    }

    private Permission getPermission(final String name) {
        Permission permission;
        try {
            permission = permissionFactory.buildFromName(name);
        } catch (final IllegalArgumentException ignore) {
            try {
                permission = permissionFactory.buildFromName(name.toUpperCase(Locale.ENGLISH));
            } catch (final IllegalArgumentException ex) {
                if ("admin".equals(name)) { //allow to use "admin" instead of "administration" to be consistent with expressions found in @PreAuthorize
                    permission = BasePermission.ADMINISTRATION;
                } else {
                    throw ex;
                }
            }
        }
        return permission;
    }
}