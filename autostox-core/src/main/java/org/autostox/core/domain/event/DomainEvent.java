package org.autostox.core.domain.event;

import java.time.Instant;
import java.util.UUID;

/** Something that happened in the core and that adapters may want to react to. */
public interface DomainEvent {

    /** Unique identifier of this event. */
    UUID id();

    /** Time at which the event occurred. */
    Instant occurredAt();
}
