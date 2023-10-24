package com.n19dccn112.service.exception;

public class NotFoundException extends RuntimeException { // lỗi logic
    public NotFoundException(Class<?> clazz, Long id) {
        super(clazz.getSimpleName() + " has id = " + id + " not found!");
    }

    public NotFoundException(String roleName) {
        super("Role has name = " + roleName + " not found!");
    }
}