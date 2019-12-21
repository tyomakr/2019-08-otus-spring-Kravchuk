package ru.otus.spring.library.webmvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.otus.spring.library.webmvc.domain.Book;
import ru.otus.spring.library.webmvc.service.AclService;

@Service
@RequiredArgsConstructor
public class AclServiceImpl implements AclService {

    private final Sid sidRoleAdmin = new GrantedAuthoritySid("ROLE_ADMIN");
    private final Sid sidRoleUser = new GrantedAuthoritySid("ROLE_USER");
    private final MutableAclService mutableAclService;


    //назначение разрешений при создании книги или перезаписи прав
    @Override
    public void initBookPermissions(String bookId, boolean setPublic, boolean alcRW) {

        ObjectIdentityImpl objectIdentity = new ObjectIdentityImpl(Book.class, bookId);

        if (alcRW) { mutableAclService.deleteAcl(objectIdentity, true); }

        PrincipalSid owner = new PrincipalSid(SecurityContextHolder.getContext().getAuthentication());

        MutableAcl acl = mutableAclService.createAcl(objectIdentity);
        acl.setOwner(owner);
        acl.insertAce(acl.getEntries().size(), BasePermission.READ, owner, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, sidRoleAdmin, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.DELETE, sidRoleAdmin, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION, sidRoleAdmin, true);

        if (setPublic) { acl.insertAce(acl.getEntries().size(), BasePermission.READ, sidRoleUser, true); }

        if (!setPublic) { acl.insertAce(acl.getEntries().size(), BasePermission.READ, sidRoleUser, false); }

        mutableAclService.updateAcl(acl);

    }


    //определяем, есть ли у книги возможность просмотра у ROLE_USER
    @Override
    public boolean isPublicBookState(String bookId) {

        ObjectIdentityImpl objectIdentity = new ObjectIdentityImpl(Book.class, bookId);
        Acl acl = mutableAclService.readAclById(objectIdentity);
        for (int i = 0; i < acl.getEntries().size(); i++) {
            if (acl.getEntries().get(i).getSid().equals(sidRoleUser)) {
                return acl.getEntries().get(i).isGranting();
            }
        }
        return false;
    }
}
