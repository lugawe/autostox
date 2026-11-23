package org.autostox.core.domain.money.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import org.autostox.core.domain.event.DomainEvent;
import org.autostox.core.domain.money.Currency;

/**
 * An exchange rate was retrieved.
 *
 * @param id unique identifier of the event
 * @param occurredAt time of retrieval
 * @param from source currency
 * @param to target currency
 * @param rate how many units of {@code to} one unit of {@code from} is worth
 */
public record ExchangeRateRetrievedEvent(UUID id, Instant occurredAt, Currency from, Currency to, BigDecimal rate)
        implements DomainEvent {

    public ExchangeRateRetrievedEvent {
        Objects.requireNonNull(id);
        Objects.requireNonNull(occurredAt);
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);
        Objects.requireNonNull(rate);
    }
}
