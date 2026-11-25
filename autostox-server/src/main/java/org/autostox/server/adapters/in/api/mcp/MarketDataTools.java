package org.autostox.server.adapters.in.api.mcp;

import java.time.LocalDate;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import org.autostox.server.adapters.in.api.json.marketdata.JsonCandleSeries;
import org.autostox.server.adapters.in.api.json.marketdata.JsonInterval;
import org.autostox.server.adapters.in.api.json.marketdata.JsonQuote;
import org.autostox.server.adapters.in.api.json.service.JsonMarketDataService;
import org.autostox.server.adapters.in.api.mcp.interceptor.CoreErrors;

import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;

@Singleton
@CoreErrors
public class MarketDataTools {

    private final JsonMarketDataService jsonMarketDataService;

    @Inject
    public MarketDataTools(JsonMarketDataService jsonMarketDataService) {
        this.jsonMarketDataService = jsonMarketDataService;
    }

    @Tool(
            name = "get_instrument_candles",
            description = "Returns the historical OHLC candles of an instrument (stock or ETF).",
            annotations =
                    @Tool.Annotations(
                            readOnlyHint = true,
                            destructiveHint = false,
                            idempotentHint = true,
                            openWorldHint = true))
    public JsonCandleSeries getInstrumentCandles(
            @ToolArg(description = "Ticker symbol of the instrument, e.g. a stock (AAPL) or an ETF (QQQ)")
                    String symbol,
            @ToolArg(description = "Length of one candle", defaultValue = "DAYS_1") JsonInterval interval,
            @ToolArg(description = "First day to include (ISO date, e.g. 2026-01-31)", required = false)
                    LocalDate start,
            @ToolArg(description = "Last day to include (ISO date, e.g. 2026-01-31)", required = false) LocalDate end,
            @ToolArg(description = "Maximum number of candles", defaultValue = "30") long limit) {

        return jsonMarketDataService.getCandles(symbol, interval, start, end, limit);
    }

    @Tool(
            name = "get_instrument_quote",
            description = "Returns the current price of an instrument (stock or ETF).",
            annotations =
                    @Tool.Annotations(
                            readOnlyHint = true,
                            destructiveHint = false,
                            idempotentHint = true,
                            openWorldHint = true))
    public JsonQuote getInstrumentQuote(
            @ToolArg(description = "Ticker symbol of the instrument, e.g. a stock (AAPL) or an ETF (QQQ)")
                    String symbol) {

        return jsonMarketDataService.getQuote(symbol);
    }
}
