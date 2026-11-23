package org.autostox.server.adapters.in.api.rest.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import org.autostox.core.ports.out.ProviderException;
import org.autostox.server.adapters.in.api.json.converter.JsonConverter;

@Provider
@ApplicationScoped
public class ProviderExceptionMapper implements ExceptionMapper<ProviderException> {

    private final JsonConverter jsonConverter;

    @Inject
    public ProviderExceptionMapper(JsonConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
    }

    @Override
    public Response toResponse(ProviderException exception) {
        return Response.status(502)
                .type(MediaType.APPLICATION_JSON)
                .entity(jsonConverter.toJsonError(502, exception.getMessage()))
                .build();
    }
}
