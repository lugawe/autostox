package org.autostox.core.domain.marketdata.event;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import org.autostox.core.domain.event.DomainEvent;
import org.autostox.core.domain.instrument.Symbol;

/**
 * A subscriber started streaming quotes of an instrument.
 *
 * @param id unique identifier of the event
 * @param occurredAt time of subscription
 * @param symbol streamed symbol
 */
public record QuoteStreamStartedEvent(UUID id, Instant occurredAt, Symbol symbol) implements DomainEvent {

    public QuoteStreamStartedEvent {
        Objects.requireNonNull(id);
        Objects.requireNonNull(occurredAt);
        Objects.requireNonNull(symbol);
    }
}
