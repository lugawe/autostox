package org.autostox.core.ports.in;

import org.autostox.core.CoreException;

public class ServiceException extends CoreException {

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable t) {
        super(message, t);
    }

    public ServiceException(Throwable t) {
        super(t);
    }
}
