package org.autostox.server.adapters.out.logging;

import org.autostox.core.ports.out.logging.Logger;

import org.slf4j.LoggerFactory;

public final class Slf4jLoggerFactory {

    private Slf4jLoggerFactory() {}

    public static Logger getLogger(Class<?> declaringClass) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(declaringClass);
        return new Slf4jLogger(logger);
    }
}
