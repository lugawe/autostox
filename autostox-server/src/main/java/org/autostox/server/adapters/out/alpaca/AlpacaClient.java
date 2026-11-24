package org.autostox.server.adapters.out.alpaca;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.Callable;

import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import org.autostox.core.CoreException;
import org.autostox.core.domain.exception.DomainExceptions;
import org.autostox.core.domain.instrument.Instrument;
import org.autostox.core.domain.instrument.Symbol;
import org.autostox.core.domain.marketdata.CandleQuery;
import org.autostox.core.domain.marketdata.CandleSeries;
import org.autostox.core.domain.marketdata.Quote;
import org.autostox.core.ports.out.ProviderException;
import org.autostox.server.adapters.out.alpaca.converter.AlpacaConverter;

import io.quarkus.cache.CacheResult;
import io.smallrye.mutiny.Multi;
import markets.alpaca.client.AlpacaCredentials;
import markets.alpaca.client.openapi.data.api.StockApi;
import markets.alpaca.client.openapi.data.http.ApiException;
import markets.alpaca.client.openapi.data.model.Sort;
import markets.alpaca.client.openapi.data.model.StockAdjustment;
import markets.alpaca.client.openapi.data.model.StockBar;
import markets.alpaca.client.openapi.data.model.StockHistoricalFeed;
import markets.alpaca.client.openapi.data.model.StockLatestFeed;
import markets.alpaca.client.openapi.data.model.StockSnapshotsRespSingle;
import markets.alpaca.client.openapi.trading.api.AssetsApi;
import markets.alpaca.client.openapi.trading.model.AssetClass;
import markets.alpaca.client.openapi.trading.model.Assets;
import markets.alpaca.client.ws.StockSource;
import markets.alpaca.client.ws.model.StockTrade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class AlpacaClient {

    private static final Logger log = LoggerFactory.getLogger(AlpacaClient.class);

    /** Alpaca's historical market data starts in 2016. */
    private static final LocalDate EARLIEST_DATA = LocalDate.of(2016, 1, 1);

    private final AlpacaConverter alpacaConverter;
    private final AssetsApi assets;
    private final StockApi stocks;
    private final StockHistoricalFeed historicalFeed;
    private final StockLatestFeed latestFeed;
    private final AlpacaPriceStream priceStream;

    @Inject
    public AlpacaClient(
            AlpacaConverter alpacaConverter,
            AssetsApi assets,
            StockApi stocks,
            StockHistoricalFeed historicalFeed,
            StockLatestFeed latestFeed,
            AlpacaCredentials credentials,
            StockSource stockSource) {

        this.alpacaConverter = alpacaConverter;
        this.assets = assets;
        this.stocks = stocks;
        this.historicalFeed = historicalFeed;
        this.latestFeed = latestFeed;
        this.priceStream = new AlpacaPriceStream(credentials, stockSource);
    }

    @PreDestroy
    public void disconnect() {
        this.priceStream.disconnect();
    }

    @CacheResult(cacheName = "instruments")
    public List<Instrument> getInstruments() {
        log.info("Alpaca API: Request assets");
        List<Assets> result = call(() -> assets.getV2Assets("active", AssetClass.US_EQUITY.getValue(), null, null));
        return result.stream().<Instrument>map(alpacaConverter::toAsset).toList();
    }

    @CacheResult(cacheName = "candles")
    public CandleSeries getCandleSeries(Symbol symbol, CandleQuery query) {
        return fetchCandleSeries(symbol, query);
    }

    @CacheResult(cacheName = "candles-intraday")
    public CandleSeries getIntradayCandleSeries(Symbol symbol, CandleQuery query) {
        return fetchCandleSeries(symbol, query);
    }

    @CacheResult(cacheName = "quotes")
    public Quote getQuote(Symbol symbol) {
        log.info("Alpaca API: Request quote {}", symbol);
        StockSnapshotsRespSingle snapshot =
                call(symbol, () -> stocks.stockSnapshotSingle(symbol.value(), latestFeed, null));
        return alpacaConverter.toQuote(symbol, snapshot);
    }

    public Multi<StockTrade> streamTrades(Symbol symbol) {
        return priceStream.trades(symbol);
    }

    private CandleSeries fetchCandleSeries(Symbol symbol, CandleQuery query) {

        log.info("Alpaca API: Request candles {} {}", symbol, query);

        OffsetDateTime start = startOfDay(query.start() != null ? query.start() : EARLIEST_DATA);
        OffsetDateTime end = query.end() != null ? startOfDay(query.end().plusDays(1)) : null;

        List<StockBar> bars = call(symbol, () -> {
            try {
                return stocks.stockBarSingle(
                                symbol.value(),
                                alpacaConverter.toTimeframe(query.interval()),
                                start,
                                end,
                                (int) Math.min(query.limit(), 10_000),
                                List.of(StockAdjustment.SPLIT),
                                null,
                                historicalFeed,
                                null,
                                null,
                                Sort.DESC)
                        .getBars();
            } catch (IllegalArgumentException e) {
                return null;
            }
        });

        if (bars == null || bars.isEmpty()) {
            throw DomainExceptions.createCandlesNotFound(symbol, query);
        }

        return alpacaConverter.toCandleSeries(symbol, query.interval(), bars);
    }

    private static OffsetDateTime startOfDay(LocalDate date) {
        return date.atStartOfDay().atOffset(ZoneOffset.UTC);
    }

    private static <T> T call(Symbol symbol, Callable<T> call) {
        return call(() -> {
            try {
                return call.call();
            } catch (ApiException e) {
                if (e.getCode() == 404) {
                    throw DomainExceptions.createSymbolNotFound(symbol);
                }
                throw e;
            }
        });
    }

    private static <T> T call(Callable<T> call) {
        try {
            return call.call();
        } catch (CoreException e) {
            throw e;
        } catch (Exception e) {
            log.error("Alpaca API: Request failed: {}", e.getMessage());
            throw new ProviderException("Alpaca API request failed", e);
        }
    }
}
