package com.kirusa.instablogs.exception;

public class NotPrimaryContactException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public NotPrimaryContactException(String message) { super(message); }
}
