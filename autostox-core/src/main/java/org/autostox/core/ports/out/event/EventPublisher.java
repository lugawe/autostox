package org.autostox.core.ports.out.event;

import org.autostox.core.domain.event.DomainEvent;

public interface EventPublisher {

    void publish(DomainEvent event);
}
