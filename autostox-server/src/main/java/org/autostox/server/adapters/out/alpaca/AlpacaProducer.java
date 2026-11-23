package org.autostox.server.adapters.out.alpaca;

import java.util.Locale;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

import markets.alpaca.client.AlpacaClientFactory;
import markets.alpaca.client.AlpacaCredentials;
import markets.alpaca.client.openapi.data.api.StockApi;
import markets.alpaca.client.openapi.data.model.StockHistoricalFeed;
import markets.alpaca.client.openapi.data.model.StockLatestFeed;
import markets.alpaca.client.ws.StockSource;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class AlpacaProducer {

    public AlpacaProducer() {}

    @Produces
    @Singleton
    public AlpacaCredentials produceCredentials(
            @ConfigProperty(name = "alpaca.api-key-id") String apiKeyId,
            @ConfigProperty(name = "alpaca.api-secret-key") String apiSecretKey) {
        return new AlpacaCredentials(apiKeyId, apiSecretKey);
    }

    @Produces
    @Singleton
    public StockApi produceStockApi(AlpacaCredentials credentials) {
        return new StockApi(AlpacaClientFactory.dataClient(credentials));
    }

    @Produces
    @Singleton
    public StockHistoricalFeed produceHistoricalFeed(@ConfigProperty(name = "alpaca.feed") String feed) {
        return StockHistoricalFeed.fromValue(feed);
    }

    @Produces
    @Singleton
    public StockLatestFeed produceLatestFeed(@ConfigProperty(name = "alpaca.feed") String feed) {
        return StockLatestFeed.fromValue(feed);
    }

    @Produces
    @Singleton
    public StockSource produceStockSource(@ConfigProperty(name = "alpaca.feed") String feed) {
        return StockSource.valueOf(feed.toUpperCase(Locale.ROOT));
    }
}
