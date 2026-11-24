package org.autostox.server.adapters.in.api.json.instrument;

import org.autostox.server.adapters.in.api.json.money.JsonCurrency;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.eclipse.microprofile.openapi.annotations.media.DiscriminatorMapping;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(
        description = "Tradable instrument",
        discriminatorProperty = "type",
        discriminatorMapping = {
            @DiscriminatorMapping(value = "ASSET", schema = JsonAsset.class),
            @DiscriminatorMapping(value = "ETF", schema = JsonEtf.class),
            @DiscriminatorMapping(value = "STOCK", schema = JsonStock.class),
        })
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(JsonAsset.class),
    @JsonSubTypes.Type(JsonEtf.class),
    @JsonSubTypes.Type(JsonStock.class)
})
public abstract class JsonInstrument {

    @Schema(description = "ISIN, null if unknown", examples = "US0378331005")
    @JsonProperty("isin")
    private String isin;

    @Schema(description = "Ticker symbol", examples = "AAPL")
    @JsonProperty("symbol")
    private String symbol;

    @Schema(description = "Display name", examples = "Apple Inc. Common Stock")
    @JsonProperty("name")
    private String name;

    @Schema(description = "Primary listing exchange, null if unknown", examples = "NASDAQ")
    @JsonProperty("exchange")
    private String exchange;

    @Schema(description = "Trading currency")
    @JsonProperty("currency")
    private JsonCurrency currency;

    protected JsonInstrument() {}

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public JsonCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(JsonCurrency currency) {
        this.currency = currency;
    }
}
