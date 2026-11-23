package org.autostox.server.adapters.in.api.mcp.marketdata.service;

import java.time.LocalDate;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.autostox.core.domain.marketdata.CandleQuery;
import org.autostox.core.domain.marketdata.CandleSeries;
import org.autostox.core.domain.marketdata.Quote;
import org.autostox.core.domain.marketdata.Symbol;
import org.autostox.core.ports.in.marketdata.MarketDataService;
import org.autostox.server.adapters.in.api.json.converter.JsonConverter;
import org.autostox.server.adapters.in.api.json.marketdata.JsonCandleSeries;
import org.autostox.server.adapters.in.api.json.marketdata.JsonInterval;
import org.autostox.server.adapters.in.api.json.marketdata.JsonQuote;

@ApplicationScoped
public class McpInstrumentService {

    private final JsonConverter jsonConverter;
    private final MarketDataService marketDataService;

    @Inject
    public McpInstrumentService(JsonConverter jsonConverter, MarketDataService marketDataService) {
        this.jsonConverter = jsonConverter;
        this.marketDataService = marketDataService;
    }

    public JsonCandleSeries getCandles(
            String symbol, JsonInterval interval, LocalDate start, LocalDate end, long limit) {

        Symbol s = new Symbol(symbol);
        CandleQuery candleQuery = new CandleQuery(jsonConverter.toInterval(interval), start, end, limit);

        CandleSeries result = marketDataService.getCandles(s, candleQuery);

        return jsonConverter.toJsonCandleSeries(result);
    }

    public JsonQuote getQuote(String symbol) {

        Quote result = marketDataService.getQuote(new Symbol(symbol));

        return jsonConverter.toJsonQuote(result);
    }
}
