package com.n19dccn112.service.exception;

import com.n19dccn112.model.dto.*;

public class ForeignKeyConstraintViolation extends RuntimeException {
    public ForeignKeyConstraintViolation(Class<?> clazz, Long id) {
        super(clazz.getSimpleName() + " has foreign key constraint violation id = " + id);
    }
}
