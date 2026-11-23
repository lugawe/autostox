package org.autostox.core.application.marketdata;

import java.time.Clock;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Flow;

import org.autostox.core.domain.marketdata.CandleQuery;
import org.autostox.core.domain.marketdata.CandleSeries;
import org.autostox.core.domain.marketdata.Quote;
import org.autostox.core.domain.marketdata.Symbol;
import org.autostox.core.domain.marketdata.event.CandlesRetrievedEvent;
import org.autostox.core.domain.marketdata.event.QuoteRetrievedEvent;
import org.autostox.core.domain.marketdata.event.QuoteStreamEndedEvent;
import org.autostox.core.domain.marketdata.event.QuoteStreamItemEvent;
import org.autostox.core.domain.marketdata.event.QuoteStreamStartedEvent;
import org.autostox.core.ports.in.marketdata.MarketDataService;
import org.autostox.core.ports.out.event.EventPublisher;
import org.autostox.core.ports.out.logging.Logger;
import org.autostox.core.ports.out.marketdata.MarketDataProvider;
import org.autostox.core.util.publisher.PublisherListener;
import org.autostox.core.util.publisher.Publishers;

public final class DefaultMarketDataService implements MarketDataService {

    private final Clock clock;
    private final Logger logger;
    private final EventPublisher eventPublisher;
    private final MarketDataProvider marketDataProvider;

    public DefaultMarketDataService(
            Clock clock, Logger logger, EventPublisher eventPublisher, MarketDataProvider marketDataProvider) {
        this.clock = Objects.requireNonNull(clock);
        this.logger = Objects.requireNonNull(logger);
        this.eventPublisher = Objects.requireNonNull(eventPublisher);
        this.marketDataProvider = Objects.requireNonNull(marketDataProvider);
    }

    @Override
    public CandleSeries getCandles(Symbol symbol, CandleQuery query) {

        logger.debug("getCandles {} {}", symbol, query);

        CandleSeries candleSeries = marketDataProvider.getCandles(symbol, query);

        eventPublisher.publish(
                new CandlesRetrievedEvent(UUID.randomUUID(), clock.instant(), symbol, query, candleSeries));

        return candleSeries;
    }

    @Override
    public Quote getQuote(Symbol symbol) {

        logger.debug("getQuote {}", symbol);

        Quote quote = marketDataProvider.getQuote(symbol);

        eventPublisher.publish(new QuoteRetrievedEvent(UUID.randomUUID(), clock.instant(), symbol, quote));

        return quote;
    }

    @Override
    public Flow.Publisher<Quote> streamQuotes(Symbol symbol) {

        logger.debug("streamQuotes {}", symbol);

        Flow.Publisher<Quote> publisher = marketDataProvider.streamQuotes(symbol);
        return Publishers.tap(publisher, new PublisherListener<>() {

            @Override
            public void onStart() {
                eventPublisher.publish(new QuoteStreamStartedEvent(UUID.randomUUID(), clock.instant(), symbol));
            }

            @Override
            public void onItem(Quote quote) {
                eventPublisher.publish(new QuoteStreamItemEvent(UUID.randomUUID(), clock.instant(), symbol, quote));
            }

            @Override
            public void onEnd(Throwable error) {
                eventPublisher.publish(new QuoteStreamEndedEvent(UUID.randomUUID(), clock.instant(), symbol, error));
            }
        });
    }
}
