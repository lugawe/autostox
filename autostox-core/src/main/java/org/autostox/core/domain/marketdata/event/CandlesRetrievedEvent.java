package org.autostox.core.domain.marketdata.event;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import org.autostox.core.domain.event.DomainEvent;
import org.autostox.core.domain.instrument.Symbol;
import org.autostox.core.domain.marketdata.CandleQuery;
import org.autostox.core.domain.marketdata.CandleSeries;

/**
 * Candles of an instrument were retrieved.
 *
 * @param id unique identifier of the event
 * @param occurredAt time of retrieval
 * @param symbol requested symbol
 * @param query requested candle query
 * @param candleSeries the retrieved candles
 */
public record CandlesRetrievedEvent(
        UUID id, Instant occurredAt, Symbol symbol, CandleQuery query, CandleSeries candleSeries)
        implements DomainEvent {

    public CandlesRetrievedEvent {
        Objects.requireNonNull(id);
        Objects.requireNonNull(occurredAt);
        Objects.requireNonNull(symbol);
        Objects.requireNonNull(query);
        Objects.requireNonNull(candleSeries);
    }
}
