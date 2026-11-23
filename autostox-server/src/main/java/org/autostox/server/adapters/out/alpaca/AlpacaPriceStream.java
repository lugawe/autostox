package org.autostox.server.adapters.out.alpaca;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.autostox.core.domain.marketdata.Symbol;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;
import markets.alpaca.client.AlpacaClientFactory;
import markets.alpaca.client.AlpacaCredentials;
import markets.alpaca.client.ws.AlpacaStockStream;
import markets.alpaca.client.ws.AlpacaStreamEnvironment;
import markets.alpaca.client.ws.StockSource;
import markets.alpaca.client.ws.StockStreamListener;
import markets.alpaca.client.ws.StockSubscription;
import markets.alpaca.client.ws.model.StockTrade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlpacaPriceStream {

    private static final Logger log = LoggerFactory.getLogger(AlpacaPriceStream.class);

    private final BroadcastProcessor<StockTrade> trades = BroadcastProcessor.create();
    private final Map<Symbol, Multi<StockTrade>> streams = new ConcurrentHashMap<>();
    private final Map<Symbol, Integer> connections = new ConcurrentHashMap<>();

    private final AlpacaStockStream stream;

    public AlpacaPriceStream(AlpacaCredentials credentials, StockSource source) {
        this.stream = AlpacaClientFactory.stockStream(
                credentials, source, AlpacaStreamEnvironment.PRODUCTION, new Listener());
    }

    public void disconnect() {
        log.info("Alpaca WebSocket: Disconnect");
        stream.close();
    }

    public Multi<StockTrade> trades(Symbol symbol) {
        return streams.computeIfAbsent(symbol, this::open);
    }

    private Multi<StockTrade> open(Symbol symbol) {
        return Multi.createFrom()
                .deferred(() -> {
                    subscribe(symbol);
                    return trades.filter(trade -> symbol.value().equals(trade.symbol()))
                            .onOverflow()
                            .dropPreviousItems();
                })
                .onTermination()
                .invoke(() -> unsubscribe(symbol))
                .broadcast()
                .withCancellationAfterLastSubscriberDeparture()
                .toAllSubscribers();
    }

    private void subscribe(Symbol symbol) {
        if (connections.merge(symbol, 1, Integer::sum) == 1) {
            log.info("Alpaca WebSocket: Subscribe {}", symbol);
            stream.subscribe(subscription(symbol));
            stream.connect();
        }
    }

    private void unsubscribe(Symbol symbol) {
        if (connections.computeIfPresent(symbol, (_, n) -> n > 1 ? n - 1 : null) == null && !stream.isClosed()) {
            log.info("Alpaca WebSocket: Unsubscribe {}", symbol);
            stream.unsubscribe(subscription(symbol));
        }
    }

    private StockSubscription subscription(Symbol symbol) {
        return StockSubscription.builder().trades(symbol.value()).build();
    }

    private class Listener implements StockStreamListener {

        private Listener() {}

        @Override
        public void onTrade(StockTrade trade) {
            log.debug("Alpaca WebSocket: Trade for {}", trade.symbol());
            trades.onNext(trade);
        }

        @Override
        public void onReconnecting(int attempt) {
            log.warn("Alpaca WebSocket: Reconnecting (attempt {})", attempt);
        }

        @Override
        public void onError(int code, String message) {
            log.error("Alpaca WebSocket: Error {} {}", code, message);
        }
    }
}
