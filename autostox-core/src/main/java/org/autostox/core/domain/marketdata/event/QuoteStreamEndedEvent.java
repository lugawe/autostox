package org.autostox.core.domain.marketdata.event;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import org.autostox.core.domain.event.DomainEvent;
import org.autostox.core.domain.marketdata.Symbol;

/**
 * A quote stream ended, either regularly or with an error.
 *
 * @param id unique identifier of the event
 * @param occurredAt time at which the stream ended
 * @param symbol streamed symbol
 * @param error the error that terminated the stream, or {@code null} if it completed regularly
 */
public record QuoteStreamEndedEvent(UUID id, Instant occurredAt, Symbol symbol, Throwable error)
        implements DomainEvent {

    public QuoteStreamEndedEvent {
        Objects.requireNonNull(id);
        Objects.requireNonNull(occurredAt);
        Objects.requireNonNull(symbol);
    }

    public boolean failed() {
        return error != null;
    }
}
