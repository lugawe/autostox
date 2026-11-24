package org.autostox.core.domain.marketdata.event;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import org.autostox.core.domain.event.DomainEvent;
import org.autostox.core.domain.instrument.Symbol;
import org.autostox.core.domain.marketdata.Quote;

/**
 * A quote update was emitted to a quote stream subscriber.
 *
 * @param id unique identifier of the event
 * @param occurredAt time of emission
 * @param symbol streamed symbol
 * @param quote the emitted quote
 */
public record QuoteStreamItemEvent(UUID id, Instant occurredAt, Symbol symbol, Quote quote) implements DomainEvent {

    public QuoteStreamItemEvent {
        Objects.requireNonNull(id);
        Objects.requireNonNull(occurredAt);
        Objects.requireNonNull(symbol);
        Objects.requireNonNull(quote);
    }
}
