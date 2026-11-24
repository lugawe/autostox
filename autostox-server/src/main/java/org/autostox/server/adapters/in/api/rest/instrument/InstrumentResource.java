package org.autostox.server.adapters.in.api.rest.instrument;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import org.autostox.server.adapters.in.api.json.instrument.JsonInstrument;
import org.autostox.server.adapters.in.api.rest.instrument.service.RestInstrumentService;

import io.smallrye.common.annotation.RunOnVirtualThread;

@RunOnVirtualThread
@Path("/instruments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InstrumentResource {

    private final RestInstrumentService restInstrumentService;

    @Inject
    public InstrumentResource(RestInstrumentService restInstrumentService) {
        this.restInstrumentService = restInstrumentService;
    }

    @GET
    public List<JsonInstrument> findInstruments(
            @QueryParam("query") String query, @QueryParam("limit") @DefaultValue("20") long limit) {

        return restInstrumentService.findInstruments(query, limit);
    }
}
