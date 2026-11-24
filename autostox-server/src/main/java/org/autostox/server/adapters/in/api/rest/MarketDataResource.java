package org.autostox.server.adapters.in.api.rest;

import java.time.LocalDate;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import org.autostox.server.adapters.in.api.json.marketdata.JsonCandleSeries;
import org.autostox.server.adapters.in.api.json.marketdata.JsonInterval;
import org.autostox.server.adapters.in.api.json.marketdata.JsonQuote;
import org.autostox.server.adapters.in.api.service.ApiMarketDataService;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Multi;
import org.jboss.resteasy.reactive.RestStreamElementType;

@RunOnVirtualThread
@Path("/marketdata")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MarketDataResource {

    private final ApiMarketDataService apiMarketDataService;

    @Inject
    public MarketDataResource(ApiMarketDataService apiMarketDataService) {
        this.apiMarketDataService = apiMarketDataService;
    }

    @GET
    @Path("/{symbol}/candles")
    public JsonCandleSeries getCandles(
            @PathParam("symbol") String symbol,
            @QueryParam("interval") @DefaultValue("DAYS_1") JsonInterval interval,
            @QueryParam("start") LocalDate start,
            @QueryParam("end") LocalDate end,
            @QueryParam("limit") @DefaultValue("30") long limit) {

        return apiMarketDataService.getCandles(symbol, interval, start, end, limit);
    }

    @GET
    @Path("/{symbol}/quote")
    public JsonQuote getQuote(@PathParam("symbol") String symbol) {

        return apiMarketDataService.getQuote(symbol);
    }

    @GET
    @Path("/{symbol}/quote/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @RestStreamElementType(MediaType.APPLICATION_JSON)
    @Blocking
    public Multi<JsonQuote> streamQuotes(@PathParam("symbol") String symbol) {

        return apiMarketDataService.streamQuotes(symbol);
    }
}
