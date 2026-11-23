package org.autostox.server.adapters.in.api.rest.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import org.autostox.core.CoreException;
import org.autostox.server.adapters.in.api.json.converter.JsonConverter;

@Provider
@ApplicationScoped
public class CoreExceptionMapper implements ExceptionMapper<CoreException> {

    private final JsonConverter jsonConverter;

    @Inject
    public CoreExceptionMapper(JsonConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
    }

    @Override
    public Response toResponse(CoreException exception) {
        return Response.status(500)
                .type(MediaType.APPLICATION_JSON)
                .entity(jsonConverter.toJsonError(500, exception.getMessage()))
                .build();
    }
}
