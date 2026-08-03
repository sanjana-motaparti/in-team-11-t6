package com.cohortteam6.exception;

public abstract class ReconException extends RuntimeException {
    protected ReconException(String message) { super(message); }
    protected ReconException(String message, Throwable cause) { super(message, cause); }
}
