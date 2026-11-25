package org.autostox.server.adapters.in.api.json.service;

import java.math.BigDecimal;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.autostox.core.domain.money.Currency;
import org.autostox.core.ports.in.money.ExchangeRateService;
import org.autostox.server.adapters.in.api.json.converter.JsonConverter;
import org.autostox.server.adapters.in.api.json.money.JsonCurrency;
import org.autostox.server.adapters.in.api.json.money.JsonExchangeRate;

@ApplicationScoped
public class JsonMoneyService {

    private final JsonConverter jsonConverter;
    private final ExchangeRateService exchangeRateService;

    @Inject
    public JsonMoneyService(JsonConverter jsonConverter, ExchangeRateService exchangeRateService) {
        this.jsonConverter = jsonConverter;
        this.exchangeRateService = exchangeRateService;
    }

    public JsonExchangeRate getExchangeRate(JsonCurrency from, JsonCurrency to) {

        Currency f = jsonConverter.toCurrency(from);
        Currency t = jsonConverter.toCurrency(to);

        BigDecimal result = exchangeRateService.getExchangeRate(f, t);

        return jsonConverter.toJsonExchangeRate(f, t, result);
    }
}
