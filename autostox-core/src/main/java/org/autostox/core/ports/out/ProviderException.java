package org.autostox.core.ports.out;

import org.autostox.core.CoreException;

public class ProviderException extends CoreException {

    public ProviderException() {
        super();
    }

    public ProviderException(String message) {
        super(message);
    }

    public ProviderException(String message, Throwable t) {
        super(message, t);
    }

    public ProviderException(Throwable t) {
        super(t);
    }
}
