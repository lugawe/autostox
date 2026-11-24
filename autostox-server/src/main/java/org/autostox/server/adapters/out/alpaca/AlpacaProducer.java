package org.autostox.server.adapters.out.alpaca;

import java.util.Locale;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

import org.autostox.server.adapters.out.alpaca.config.AlpacaConfig;

import markets.alpaca.client.AlpacaClientFactory;
import markets.alpaca.client.AlpacaCredentials;
import markets.alpaca.client.openapi.data.api.StockApi;
import markets.alpaca.client.openapi.data.model.StockHistoricalFeed;
import markets.alpaca.client.openapi.data.model.StockLatestFeed;
import markets.alpaca.client.openapi.trading.api.AssetsApi;
import markets.alpaca.client.ws.StockSource;

@ApplicationScoped
public class AlpacaProducer {

    public AlpacaProducer() {}

    @Produces
    @Singleton
    public AlpacaCredentials produceCredentials(AlpacaConfig config) {
        return new AlpacaCredentials(config.apiKeyId(), config.apiSecretKey());
    }

    @Produces
    @Singleton
    public AssetsApi produceAssetsApi(AlpacaCredentials credentials, AlpacaConfig config) {
        return new AssetsApi(AlpacaClientFactory.tradingClient(credentials, config.tradingEnvironment()));
    }

    @Produces
    @Singleton
    public StockApi produceStockApi(AlpacaCredentials credentials) {
        return new StockApi(AlpacaClientFactory.dataClient(credentials));
    }

    @Produces
    @Singleton
    public StockHistoricalFeed produceHistoricalFeed(AlpacaConfig config) {
        return StockHistoricalFeed.fromValue(config.feed());
    }

    @Produces
    @Singleton
    public StockLatestFeed produceLatestFeed(AlpacaConfig config) {
        return StockLatestFeed.fromValue(config.feed());
    }

    @Produces
    @Singleton
    public StockSource produceStockSource(AlpacaConfig config) {
        return StockSource.valueOf(config.feed().toUpperCase(Locale.ROOT));
    }
}
