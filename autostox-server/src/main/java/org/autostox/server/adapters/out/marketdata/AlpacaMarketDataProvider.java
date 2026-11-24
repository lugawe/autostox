package org.autostox.server.adapters.out.marketdata;

import java.time.OffsetDateTime;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.autostox.core.domain.instrument.Symbol;
import org.autostox.core.domain.marketdata.CandleQuery;
import org.autostox.core.domain.marketdata.CandleSeries;
import org.autostox.core.domain.marketdata.Quote;
import org.autostox.core.ports.out.marketdata.MarketDataProvider;
import org.autostox.server.adapters.out.alpaca.AlpacaClient;

import io.smallrye.mutiny.Multi;
import markets.alpaca.client.ws.model.StockTrade;

@ApplicationScoped
public class AlpacaMarketDataProvider implements MarketDataProvider {

    private final AlpacaClient client;

    @Inject
    public AlpacaMarketDataProvider(AlpacaClient client) {
        this.client = client;
    }

    @Override
    public CandleSeries getCandles(Symbol symbol, CandleQuery query) {
        return query.interval().isIntraday()
                ? client.getIntradayCandleSeries(symbol, query)
                : client.getCandleSeries(symbol, query);
    }

    @Override
    public Quote getQuote(Symbol symbol) {
        return client.getQuote(symbol);
    }

    @Override
    public Multi<Quote> streamQuotes(Symbol symbol) {
        Quote quote = client.getQuote(symbol);
        return client.streamTrades(symbol).onItem().scan(() -> quote, AlpacaMarketDataProvider::applyTrade);
    }

    private static Quote applyTrade(Quote quote, StockTrade trade) {
        long timestamp = OffsetDateTime.parse(trade.timestamp()).toEpochSecond();
        return quote.updatePrice(timestamp, trade.price(), quote.volume() + trade.size());
    }
}
