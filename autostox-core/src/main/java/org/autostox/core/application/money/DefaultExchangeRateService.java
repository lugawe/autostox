package org.autostox.core.application.money;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.Objects;
import java.util.UUID;

import org.autostox.core.domain.money.Currency;
import org.autostox.core.domain.money.event.ExchangeRateRetrievedEvent;
import org.autostox.core.ports.in.money.ExchangeRateService;
import org.autostox.core.ports.out.event.EventPublisher;
import org.autostox.core.ports.out.logging.Logger;
import org.autostox.core.ports.out.money.ExchangeRateProvider;

public final class DefaultExchangeRateService implements ExchangeRateService {

    private final Clock clock;
    private final Logger logger;
    private final EventPublisher eventPublisher;
    private final ExchangeRateProvider exchangeRateProvider;

    public DefaultExchangeRateService(
            Clock clock, Logger logger, EventPublisher eventPublisher, ExchangeRateProvider exchangeRateProvider) {
        this.clock = Objects.requireNonNull(clock);
        this.logger = Objects.requireNonNull(logger);
        this.eventPublisher = Objects.requireNonNull(eventPublisher);
        this.exchangeRateProvider = Objects.requireNonNull(exchangeRateProvider);
    }

    @Override
    public BigDecimal getExchangeRate(Currency from, Currency to) {

        logger.debug("getExchangeRate {} to {}", from, to);

        BigDecimal rate = from == to ? BigDecimal.ONE : exchangeRateProvider.getExchangeRate(from, to);

        eventPublisher.publish(new ExchangeRateRetrievedEvent(UUID.randomUUID(), clock.instant(), from, to, rate));

        return rate;
    }
}
