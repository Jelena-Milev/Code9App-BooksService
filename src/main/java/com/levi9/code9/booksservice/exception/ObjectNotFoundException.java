package com.levi9.code9.booksservice.exception;

public class ObjectNotFoundException extends RuntimeException{
    public ObjectNotFoundException(String objectClass, Long id) {
        super(objectClass+" with id: "+id+" not found");
    }
}
