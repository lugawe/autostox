package org.autostox.server.adapters.out.alpaca.converter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.autostox.core.domain.exception.DomainExceptions;
import org.autostox.core.domain.marketdata.Candle;
import org.autostox.core.domain.marketdata.CandleSeries;
import org.autostox.core.domain.marketdata.Interval;
import org.autostox.core.domain.marketdata.Quote;
import org.autostox.core.domain.marketdata.Symbol;
import org.autostox.core.domain.money.Currency;

import markets.alpaca.client.openapi.data.model.StockBar;
import markets.alpaca.client.openapi.data.model.StockSnapshotsRespSingle;

/** Alpaca prices are always USD, since no other currency is requested. */
@ApplicationScoped
public class AlpacaConverter {

    @Inject
    public AlpacaConverter() {}

    public String toTimeframe(Interval interval) {
        return switch (interval) {
            case MINUTES_1 -> "1Min";
            case MINUTES_5 -> "5Min";
            case HOURS_1 -> "1Hour";
            case DAYS_1 -> "1Day";
            case WEEKS_1 -> "1Week";
        };
    }

    public CandleSeries toCandleSeries(Symbol symbol, Interval interval, List<StockBar> bars) {
        List<Candle> candles = bars.stream().map(this::toCandle).toList();
        return new CandleSeries(symbol, interval, Currency.USD, candles);
    }

    public Candle toCandle(StockBar bar) {
        return new Candle(
                bar.getT().toEpochSecond(),
                toDecimal(bar.getO()),
                toDecimal(bar.getH()),
                toDecimal(bar.getL()),
                toDecimal(bar.getC()),
                bar.getV());
    }

    public Quote toQuote(Symbol symbol, StockSnapshotsRespSingle snapshot) {

        if (snapshot.getLatestTrade() == null || snapshot.getDailyBar() == null || snapshot.getPrevDailyBar() == null) {
            throw DomainExceptions.createSymbolNotFound(symbol);
        }

        StockBar day = snapshot.getDailyBar();
        BigDecimal price = toDecimal(snapshot.getLatestTrade().getP());
        BigDecimal previousClose = toDecimal(snapshot.getPrevDailyBar().getC());
        BigDecimal change = price.subtract(previousClose);

        return new Quote(
                snapshot.getLatestTrade().getT().toEpochSecond(),
                symbol,
                Currency.USD,
                price,
                toDecimal(day.getO()),
                toDecimal(day.getH()),
                toDecimal(day.getL()),
                previousClose,
                change,
                change.multiply(BigDecimal.valueOf(100)).divide(previousClose, 6, RoundingMode.HALF_UP),
                day.getV());
    }

    private static BigDecimal toDecimal(Double value) {
        return BigDecimal.valueOf(value);
    }
}
