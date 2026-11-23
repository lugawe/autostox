package org.autostox.server.adapters.in.api.json.money;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "Exchange rate")
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonExchangeRate {

    @Schema(description = "Source currency")
    @JsonProperty("from")
    private JsonCurrency from;

    @Schema(description = "Target currency")
    @JsonProperty("to")
    private JsonCurrency to;

    @Schema(description = "Units of the target currency per unit of the source currency", examples = "0.91151")
    @JsonProperty("rate")
    private BigDecimal rate;

    public JsonExchangeRate() {}

    public JsonCurrency getFrom() {
        return from;
    }

    public void setFrom(JsonCurrency from) {
        this.from = from;
    }

    public JsonCurrency getTo() {
        return to;
    }

    public void setTo(JsonCurrency to) {
        this.to = to;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
