package org.autostox.server.adapters.in.api.json.converter;

import java.math.BigDecimal;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.autostox.core.domain.instrument.Asset;
import org.autostox.core.domain.instrument.Etf;
import org.autostox.core.domain.instrument.Instrument;
import org.autostox.core.domain.instrument.Stock;
import org.autostox.core.domain.marketdata.Candle;
import org.autostox.core.domain.marketdata.CandleSeries;
import org.autostox.core.domain.marketdata.Direction;
import org.autostox.core.domain.marketdata.Interval;
import org.autostox.core.domain.marketdata.Quote;
import org.autostox.core.domain.money.Currency;
import org.autostox.server.adapters.in.api.json.error.JsonError;
import org.autostox.server.adapters.in.api.json.instrument.JsonAsset;
import org.autostox.server.adapters.in.api.json.instrument.JsonEtf;
import org.autostox.server.adapters.in.api.json.instrument.JsonInstrument;
import org.autostox.server.adapters.in.api.json.instrument.JsonStock;
import org.autostox.server.adapters.in.api.json.marketdata.JsonCandle;
import org.autostox.server.adapters.in.api.json.marketdata.JsonCandleSeries;
import org.autostox.server.adapters.in.api.json.marketdata.JsonDirection;
import org.autostox.server.adapters.in.api.json.marketdata.JsonInterval;
import org.autostox.server.adapters.in.api.json.marketdata.JsonQuote;
import org.autostox.server.adapters.in.api.json.money.JsonCurrency;
import org.autostox.server.adapters.in.api.json.money.JsonExchangeRate;

@ApplicationScoped
public class JsonConverter {

    @Inject
    public JsonConverter() {}

    public JsonInstrument toJsonInstrument(Instrument instrument) {

        JsonInstrument result = switch (instrument) {
            case Stock _ -> new JsonStock();
            case Etf _ -> new JsonEtf();
            case Asset _ -> new JsonAsset();
        };

        result.setIsin(instrument.isin() != null ? instrument.isin().value() : null);
        result.setSymbol(instrument.symbol().value());
        result.setName(instrument.name());
        result.setExchange(instrument.exchange());
        result.setCurrency(toJsonCurrency(instrument.currency()));

        return result;
    }

    public JsonQuote toJsonQuote(Quote quote) {

        JsonQuote result = new JsonQuote();

        result.setTimestamp(quote.timestamp());
        result.setSymbol(quote.symbol().value());
        result.setCurrency(toJsonCurrency(quote.currency()));
        result.setPrice(quote.price());
        result.setOpen(quote.open());
        result.setHigh(quote.high());
        result.setLow(quote.low());
        result.setPreviousClose(quote.previousClose());
        result.setChange(quote.change());
        result.setPercentChange(quote.percentChange());
        result.setVolume(quote.volume());

        return result;
    }

    public JsonCandleSeries toJsonCandleSeries(CandleSeries candleSeries) {

        JsonCandleSeries result = new JsonCandleSeries();

        result.setSymbol(candleSeries.symbol().value());
        result.setInterval(toJsonInterval(candleSeries.interval()));
        result.setCurrency(toJsonCurrency(candleSeries.currency()));
        result.setAggregate(toJsonCandle(candleSeries.getAggregate()));
        result.setCandles(
                candleSeries.candles().stream().map(this::toJsonCandle).toList());

        return result;
    }

    public JsonExchangeRate toJsonExchangeRate(Currency from, Currency to, BigDecimal rate) {

        JsonExchangeRate result = new JsonExchangeRate();

        result.setFrom(toJsonCurrency(from));
        result.setTo(toJsonCurrency(to));
        result.setRate(rate);

        return result;
    }

    public JsonCandle toJsonCandle(Candle candle) {

        if (candle == null) {
            return null;
        }

        JsonCandle result = new JsonCandle();

        result.setTimestamp(candle.timestamp());
        result.setOpen(candle.open());
        result.setHigh(candle.high());
        result.setLow(candle.low());
        result.setClose(candle.close());
        result.setVolume(candle.volume());
        result.setDirection(toJsonDirection(candle.getDirection()));

        return result;
    }

    public Interval toInterval(JsonInterval interval) {
        return switch (interval) {
            case MINUTES_1 -> Interval.MINUTES_1;
            case MINUTES_5 -> Interval.MINUTES_5;
            case HOURS_1 -> Interval.HOURS_1;
            case DAYS_1 -> Interval.DAYS_1;
            case WEEKS_1 -> Interval.WEEKS_1;
        };
    }

    public JsonInterval toJsonInterval(Interval interval) {
        return switch (interval) {
            case MINUTES_1 -> JsonInterval.MINUTES_1;
            case MINUTES_5 -> JsonInterval.MINUTES_5;
            case HOURS_1 -> JsonInterval.HOURS_1;
            case DAYS_1 -> JsonInterval.DAYS_1;
            case WEEKS_1 -> JsonInterval.WEEKS_1;
        };
    }

    public JsonDirection toJsonDirection(Direction direction) {
        return switch (direction) {
            case UP -> JsonDirection.UP;
            case DOWN -> JsonDirection.DOWN;
            case FLAT -> JsonDirection.FLAT;
        };
    }

    public Currency toCurrency(JsonCurrency currency) {
        return switch (currency) {
            case USD -> Currency.USD;
            case EUR -> Currency.EUR;
            case JPY -> Currency.JPY;
            case GBP -> Currency.GBP;
            case CNY -> Currency.CNY;
            case AUD -> Currency.AUD;
            case CAD -> Currency.CAD;
            case CHF -> Currency.CHF;
            case HKD -> Currency.HKD;
            case SGD -> Currency.SGD;
            case SEK -> Currency.SEK;
            case KRW -> Currency.KRW;
        };
    }

    public JsonCurrency toJsonCurrency(Currency currency) {
        return switch (currency) {
            case USD -> JsonCurrency.USD;
            case EUR -> JsonCurrency.EUR;
            case JPY -> JsonCurrency.JPY;
            case GBP -> JsonCurrency.GBP;
            case CNY -> JsonCurrency.CNY;
            case AUD -> JsonCurrency.AUD;
            case CAD -> JsonCurrency.CAD;
            case CHF -> JsonCurrency.CHF;
            case HKD -> JsonCurrency.HKD;
            case SGD -> JsonCurrency.SGD;
            case SEK -> JsonCurrency.SEK;
            case KRW -> JsonCurrency.KRW;
        };
    }

    public JsonError toJsonError(int status, String message) {

        JsonError result = new JsonError();

        result.setStatus(status);
        result.setMessage(message);

        return result;
    }
}
