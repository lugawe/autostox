package org.autostox.server.adapters.in.api.rest.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import org.autostox.core.domain.exception.NotFoundException;
import org.autostox.server.adapters.in.api.json.converter.JsonConverter;

@Provider
@ApplicationScoped
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    private final JsonConverter jsonConverter;

    @Inject
    public NotFoundExceptionMapper(JsonConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
    }

    @Override
    public Response toResponse(NotFoundException exception) {
        return Response.status(404)
                .type(MediaType.APPLICATION_JSON)
                .entity(jsonConverter.toJsonError(404, exception.getMessage()))
                .build();
    }
}
