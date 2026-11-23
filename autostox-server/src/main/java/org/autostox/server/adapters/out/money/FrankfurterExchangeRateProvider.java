package org.autostox.server.adapters.out.money;

import java.math.BigDecimal;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.WebApplicationException;

import org.autostox.core.domain.exception.DomainExceptions;
import org.autostox.core.domain.money.Currency;
import org.autostox.core.ports.out.ProviderException;
import org.autostox.core.ports.out.money.ExchangeRateProvider;
import org.autostox.server.adapters.out.frankfurter.FrankfurterApi;
import org.autostox.server.adapters.out.frankfurter.json.JsonFrankfurterRate;

import io.quarkus.cache.CacheResult;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class FrankfurterExchangeRateProvider implements ExchangeRateProvider {

    private final FrankfurterApi api;

    @Inject
    public FrankfurterExchangeRateProvider(@RestClient FrankfurterApi api) {
        this.api = api;
    }

    @Override
    @CacheResult(cacheName = "exchange-rates")
    public BigDecimal getExchangeRate(Currency from, Currency to) {

        JsonFrankfurterRate frankfurterRate;
        try {
            frankfurterRate = api.getRate(from.isoCode(), to.isoCode());
        } catch (WebApplicationException | ProcessingException e) {
            throw new ProviderException("Frankfurter API request failed", e);
        }

        BigDecimal rate = frankfurterRate.rate();
        if (rate == null) {
            throw DomainExceptions.createExchangeRateNotFound(from, to);
        }

        return rate;
    }
}
