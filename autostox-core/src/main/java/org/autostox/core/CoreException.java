package org.autostox.core;

public abstract class CoreException extends RuntimeException {

    protected CoreException() {
        super();
    }

    protected CoreException(String message) {
        super(message);
    }

    protected CoreException(String message, Throwable t) {
        super(message, t);
    }

    protected CoreException(Throwable t) {
        super(t);
    }
}
