package org.autostox.server.adapters.in.api.json.marketdata;

import java.util.List;

import org.autostox.server.adapters.in.api.json.money.JsonCurrency;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "Candles of an instrument")
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonCandleSeries {

    @Schema(description = "Ticker symbol", examples = "AAPL")
    @JsonProperty("symbol")
    private String symbol;

    @Schema(description = "Length of one candle")
    @JsonProperty("interval")
    private JsonInterval interval;

    @Schema(description = "Currency of all prices")
    @JsonProperty("currency")
    private JsonCurrency currency;

    @Schema(description = "All candles combined into one, null if empty")
    @JsonProperty("aggregate")
    private JsonCandle aggregate;

    @Schema(description = "Candles, newest first")
    @JsonProperty("candles")
    private List<JsonCandle> candles;

    public JsonCandleSeries() {}

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public JsonInterval getInterval() {
        return interval;
    }

    public void setInterval(JsonInterval interval) {
        this.interval = interval;
    }

    public JsonCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(JsonCurrency currency) {
        this.currency = currency;
    }

    public JsonCandle getAggregate() {
        return aggregate;
    }

    public void setAggregate(JsonCandle aggregate) {
        this.aggregate = aggregate;
    }

    public List<JsonCandle> getCandles() {
        return candles;
    }

    public void setCandles(List<JsonCandle> candles) {
        this.candles = candles;
    }
}
