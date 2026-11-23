package org.autostox.core.application.event;

import java.util.Objects;

import org.autostox.core.domain.event.DomainEvent;
import org.autostox.core.ports.out.event.EventPublisher;
import org.autostox.core.ports.out.logging.Logger;

public final class SafeEventPublisher implements EventPublisher {

    private final Logger logger;
    private final EventPublisher delegate;

    public SafeEventPublisher(Logger logger, EventPublisher delegate) {
        this.logger = Objects.requireNonNull(logger);
        this.delegate = Objects.requireNonNull(delegate);
    }

    @Override
    public void publish(DomainEvent event) {
        Objects.requireNonNull(event);
        try {
            delegate.publish(event);
        } catch (Exception e) {
            logger.error("Failed to publish event {}", event, e);
        }
    }
}
