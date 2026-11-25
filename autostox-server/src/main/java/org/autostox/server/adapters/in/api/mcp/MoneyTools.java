package org.autostox.server.adapters.in.api.mcp;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import org.autostox.server.adapters.in.api.json.money.JsonCurrency;
import org.autostox.server.adapters.in.api.json.money.JsonExchangeRate;
import org.autostox.server.adapters.in.api.json.service.JsonMoneyService;
import org.autostox.server.adapters.in.api.mcp.interceptor.CoreErrors;

import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;

@Singleton
@CoreErrors
public class MoneyTools {

    private final JsonMoneyService jsonMoneyService;

    @Inject
    public MoneyTools(JsonMoneyService jsonMoneyService) {
        this.jsonMoneyService = jsonMoneyService;
    }

    @Tool(
            name = "get_exchange_rate",
            description = "Returns how many units of the target currency one unit of the source currency is worth.",
            annotations =
                    @Tool.Annotations(
                            readOnlyHint = true,
                            destructiveHint = false,
                            idempotentHint = true,
                            openWorldHint = true))
    public JsonExchangeRate getExchangeRate(
            @ToolArg(description = "Source currency (ISO code, e.g. USD)") JsonCurrency from,
            @ToolArg(description = "Target currency (ISO code, e.g. EUR)") JsonCurrency to) {

        return jsonMoneyService.getExchangeRate(from, to);
    }
}
