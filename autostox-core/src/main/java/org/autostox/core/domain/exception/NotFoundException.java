package org.autostox.core.domain.exception;

import org.autostox.core.CoreException;

public class NotFoundException extends CoreException {

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable t) {
        super(message, t);
    }

    public NotFoundException(Throwable t) {
        super(t);
    }
}
