package org.autostox.server.adapters.in.api.json.marketdata;

import java.math.BigDecimal;

import org.autostox.server.adapters.in.api.json.money.JsonCurrency;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "Current price of an instrument")
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonQuote {

    @Schema(description = "Last price update (Unix seconds, UTC)", examples = "1795132800")
    @JsonProperty("timestamp")
    private long timestamp;

    @Schema(description = "Ticker symbol", examples = "AAPL")
    @JsonProperty("symbol")
    private String symbol;

    @Schema(description = "Currency of all prices")
    @JsonProperty("currency")
    private JsonCurrency currency;

    @Schema(description = "Last price", examples = "342.56")
    @JsonProperty("price")
    private BigDecimal price;

    @Schema(description = "First price of the day", examples = "352.87")
    @JsonProperty("open")
    private BigDecimal open;

    @Schema(description = "Highest price of the day", examples = "354.34")
    @JsonProperty("high")
    private BigDecimal high;

    @Schema(description = "Lowest price of the day", examples = "340.56")
    @JsonProperty("low")
    private BigDecimal low;

    @Schema(description = "Last price of the previous day", examples = "352.91")
    @JsonProperty("previous_close")
    private BigDecimal previousClose;

    @Schema(description = "Change since previous close", examples = "-10.35")
    @JsonProperty("change")
    private BigDecimal change;

    @Schema(description = "Change since previous close in percent", examples = "-2.93")
    @JsonProperty("percent_change")
    private BigDecimal percentChange;

    @Schema(description = "Traded units of the day", examples = "80464200")
    @JsonProperty("volume")
    private long volume;

    public JsonQuote() {}

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public JsonCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(JsonCurrency currency) {
        this.currency = currency;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getPreviousClose() {
        return previousClose;
    }

    public void setPreviousClose(BigDecimal previousClose) {
        this.previousClose = previousClose;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }

    public BigDecimal getPercentChange() {
        return percentChange;
    }

    public void setPercentChange(BigDecimal percentChange) {
        this.percentChange = percentChange;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }
}
