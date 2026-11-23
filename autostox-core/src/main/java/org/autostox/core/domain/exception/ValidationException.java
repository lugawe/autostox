package org.autostox.core.domain.exception;

import org.autostox.core.CoreException;

public class ValidationException extends CoreException {

    public ValidationException() {
        super();
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable t) {
        super(message, t);
    }

    public ValidationException(Throwable t) {
        super(t);
    }
}
