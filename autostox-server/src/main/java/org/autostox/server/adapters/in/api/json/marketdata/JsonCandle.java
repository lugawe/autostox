package org.autostox.server.adapters.in.api.json.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "OHLC candle of one period")
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonCandle {

    @Schema(description = "Start of the period (Unix seconds, UTC)", examples = "1795132800")
    @JsonProperty("timestamp")
    private long timestamp;

    @Schema(description = "First price", examples = "352.87")
    @JsonProperty("open")
    private BigDecimal open;

    @Schema(description = "Highest price", examples = "354.34")
    @JsonProperty("high")
    private BigDecimal high;

    @Schema(description = "Lowest price", examples = "340.56")
    @JsonProperty("low")
    private BigDecimal low;

    @Schema(description = "Last price", examples = "342.56")
    @JsonProperty("close")
    private BigDecimal close;

    @Schema(description = "Traded units", examples = "80464200")
    @JsonProperty("volume")
    private long volume;

    @Schema(description = "Price movement from open to close")
    @JsonProperty("direction")
    private JsonDirection direction;

    public JsonCandle() {}

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
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

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public JsonDirection getDirection() {
        return direction;
    }

    public void setDirection(JsonDirection direction) {
        this.direction = direction;
    }
}
