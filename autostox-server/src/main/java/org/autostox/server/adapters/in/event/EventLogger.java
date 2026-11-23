package org.autostox.server.adapters.in.event;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;

import org.autostox.core.domain.event.DomainEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class EventLogger {

    private static final Logger log = LoggerFactory.getLogger(EventLogger.class);

    @Inject
    public EventLogger() {}

    public void onEvent(@ObservesAsync DomainEvent event) {
        log.info("Event {}", event);
    }
}
