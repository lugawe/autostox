package org.autostox.server.cdi.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.NotificationOptions;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

import org.autostox.core.application.event.SafeEventPublisher;
import org.autostox.core.domain.event.DomainEvent;
import org.autostox.core.ports.out.event.EventPublisher;
import org.autostox.core.ports.out.logging.Logger;
import org.autostox.server.adapters.out.logging.Slf4jLoggerFactory;

@ApplicationScoped
public class EventProducer {

    private final ExecutorService executor = Executors.newSingleThreadExecutor(
            Thread.ofVirtual().name("domain-events").factory());

    @Inject
    private Event<DomainEvent> event;

    public EventProducer() {}

    @Produces
    @ApplicationScoped
    public EventPublisher produceEventPublisher() {

        Logger logger = Slf4jLoggerFactory.getLogger(SafeEventPublisher.class);

        NotificationOptions options = NotificationOptions.ofExecutor(executor);

        return new SafeEventPublisher(
                logger,
                e -> event.fireAsync(e, options).exceptionally(t -> {
                    logger.error("Observer failed for event {}", e.getClass().getSimpleName(), t);
                    return null;
                }));
    }

    @PreDestroy
    protected void shutdown() {
        executor.close();
    }
}
