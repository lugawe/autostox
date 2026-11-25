package org.autostox.server.adapters.in.api.mcp;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import org.autostox.server.adapters.in.api.json.instrument.JsonInstrument;
import org.autostox.server.adapters.in.api.json.service.JsonInstrumentService;
import org.autostox.server.adapters.in.api.mcp.interceptor.CoreErrors;

import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;

@Singleton
@CoreErrors
public class InstrumentTools {

    private final JsonInstrumentService jsonInstrumentService;

    @Inject
    public InstrumentTools(JsonInstrumentService jsonInstrumentService) {
        this.jsonInstrumentService = jsonInstrumentService;
    }

    @Tool(
            name = "search_instruments",
            description = "Searches instruments (stocks and ETFs) by ticker symbol or name.",
            annotations =
                    @Tool.Annotations(
                            readOnlyHint = true,
                            destructiveHint = false,
                            idempotentHint = true,
                            openWorldHint = true))
    public List<JsonInstrument> searchInstruments(
            @ToolArg(description = "Ticker symbol or part of the name, e.g. AAPL or Apple") String query,
            @ToolArg(description = "Maximum number of instruments", defaultValue = "20") long limit) {

        return jsonInstrumentService.findInstruments(query, limit);
    }
}
