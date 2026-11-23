package org.autostox.server.adapters.in.api.rest.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import org.autostox.core.domain.exception.ValidationException;
import org.autostox.server.adapters.in.api.json.converter.JsonConverter;

@Provider
@ApplicationScoped
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    private final JsonConverter jsonConverter;

    @Inject
    public ValidationExceptionMapper(JsonConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
    }

    @Override
    public Response toResponse(ValidationException exception) {
        return Response.status(400)
                .type(MediaType.APPLICATION_JSON)
                .entity(jsonConverter.toJsonError(400, exception.getMessage()))
                .build();
    }
}
