package org.autostox.core.domain.instrument.event;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.autostox.core.domain.event.DomainEvent;
import org.autostox.core.domain.instrument.Instrument;

/**
 * Instruments were searched.
 *
 * @param id unique identifier of the event
 * @param occurredAt time of the search
 * @param query search term
 * @param instruments matching instruments, possibly empty
 */
public record InstrumentsFoundEvent(UUID id, Instant occurredAt, String query, List<Instrument> instruments)
        implements DomainEvent {

    public InstrumentsFoundEvent {
        Objects.requireNonNull(id);
        Objects.requireNonNull(occurredAt);
        Objects.requireNonNull(query);
        instruments = List.copyOf(Objects.requireNonNull(instruments));
    }
}
