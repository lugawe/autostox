package org.autostox.core.domain.marketdata.event;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import org.autostox.core.domain.event.DomainEvent;
import org.autostox.core.domain.marketdata.Quote;
import org.autostox.core.domain.marketdata.Symbol;

/**
 * A single quote of an instrument was retrieved.
 *
 * @param id unique identifier of the event
 * @param occurredAt time of retrieval
 * @param symbol requested symbol
 * @param quote the retrieved quote
 */
public record QuoteRetrievedEvent(UUID id, Instant occurredAt, Symbol symbol, Quote quote) implements DomainEvent {

    public QuoteRetrievedEvent {
        Objects.requireNonNull(id);
        Objects.requireNonNull(occurredAt);
        Objects.requireNonNull(symbol);
        Objects.requireNonNull(quote);
    }
}
