package com.levi9.code9.booksservice.exception;

public class ObjectAlreadyExistsException extends RuntimeException {

    public ObjectAlreadyExistsException(String objectClass) {
        super(objectClass +" already exists");
    }
}
