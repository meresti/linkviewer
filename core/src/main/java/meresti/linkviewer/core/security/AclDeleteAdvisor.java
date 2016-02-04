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
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@SuppressWarnings("serial")
public class AclDeleteAdvisor extends AbstractAnnotationBasedAdvisor<ContentRoomService, AclDelete, ContentRoom> {

    private final MutableAclService mutableAclService;

    @Autowired
    public AclDeleteAdvisor(final MutableAclService mutableAclService) {
        super(ContentRoomService.class, AclDelete.class, ContentRoom.class);

        this.mutableAclService = mutableAclService;
    }

    @Override
    protected void advice(final ContentRoom returnValue, final AclDelete annotation) {
        final ObjectIdentity objectIdentity = new ObjectIdentityImpl(ContentRoom.class, returnValue.getId().toString());

        final MutableAcl acl = (MutableAcl) mutableAclService.readAclById(objectIdentity);

        final List<AccessControlEntry> entries = acl.getEntries();
        for (int i = 0; i < entries.size(); i++) {
            acl.deleteAce(i);
        }

        mutableAclService.updateAcl(acl);
    }
}
