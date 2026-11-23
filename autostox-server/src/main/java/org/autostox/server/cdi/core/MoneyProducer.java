package org.autostox.server.cdi.core;

import java.time.Clock;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

import org.autostox.core.application.money.DefaultExchangeRateService;
import org.autostox.core.ports.in.money.ExchangeRateService;
import org.autostox.core.ports.out.event.EventPublisher;
import org.autostox.core.ports.out.logging.Logger;
import org.autostox.core.ports.out.money.ExchangeRateProvider;
import org.autostox.server.adapters.out.logging.Slf4jLoggerFactory;

@ApplicationScoped
public class MoneyProducer {

    @Inject
    private EventPublisher eventPublisher;

    @Inject
    private ExchangeRateProvider exchangeRateProvider;

    public MoneyProducer() {}

    @Produces
    public ExchangeRateService produceExchangeRateService() {

        Logger logger = Slf4jLoggerFactory.getLogger(DefaultExchangeRateService.class);

        return new DefaultExchangeRateService(Clock.systemUTC(), logger, eventPublisher, exchangeRateProvider);
    }
}
