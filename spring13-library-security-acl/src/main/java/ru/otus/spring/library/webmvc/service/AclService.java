package ru.otus.spring.library.webmvc.service;

public interface AclService {

    void initBookPermissions(String bookId, boolean isPublic, boolean aclRW);

    boolean isPublicBookState(String bookId);
}
