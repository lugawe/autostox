package org.autostox.server.adapters.in.api.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import org.autostox.server.adapters.in.api.json.money.JsonCurrency;
import org.autostox.server.adapters.in.api.json.money.JsonExchangeRate;
import org.autostox.server.adapters.in.api.json.service.JsonMoneyService;

import io.smallrye.common.annotation.RunOnVirtualThread;

@RunOnVirtualThread
@Path("/money")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MoneyResource {

    private final JsonMoneyService jsonMoneyService;

    @Inject
    public MoneyResource(JsonMoneyService jsonMoneyService) {
        this.jsonMoneyService = jsonMoneyService;
    }

    @GET
    @Path("/exchange-rates/{from}/{to}")
    public JsonExchangeRate getExchangeRate(@PathParam("from") JsonCurrency from, @PathParam("to") JsonCurrency to) {

        return jsonMoneyService.getExchangeRate(from, to);
    }
}
