package org.autostox.server.adapters.out.frankfurter;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.autostox.server.adapters.out.frankfurter.json.JsonFrankfurterRate;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/** Daily reference rates of the European Central Bank, see <a href="https://frankfurter.dev">...</a> */
@RegisterRestClient(configKey = "frankfurter")
@Produces(MediaType.APPLICATION_JSON)
public interface FrankfurterApi {

    @GET
    @Path("/rate/{base}/{quote}")
    JsonFrankfurterRate getRate(@PathParam("base") String base, @PathParam("quote") String quote);
}
