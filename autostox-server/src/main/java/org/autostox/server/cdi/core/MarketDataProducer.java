package org.autostox.server.cdi.core;

import java.time.Clock;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

import org.autostox.core.application.marketdata.DefaultMarketDataService;
import org.autostox.core.ports.in.marketdata.MarketDataService;
import org.autostox.core.ports.out.event.EventPublisher;
import org.autostox.core.ports.out.logging.Logger;
import org.autostox.core.ports.out.marketdata.MarketDataProvider;
import org.autostox.server.adapters.out.logging.Slf4jLoggerFactory;

@ApplicationScoped
public class MarketDataProducer {

    @Inject
    private EventPublisher eventPublisher;

    @Inject
    private MarketDataProvider marketDataProvider;

    public MarketDataProducer() {}

    @Produces
    public MarketDataService produceMarketDataService() {

        Logger logger = Slf4jLoggerFactory.getLogger(DefaultMarketDataService.class);

        return new DefaultMarketDataService(Clock.systemUTC(), logger, eventPublisher, marketDataProvider);
    }
}
