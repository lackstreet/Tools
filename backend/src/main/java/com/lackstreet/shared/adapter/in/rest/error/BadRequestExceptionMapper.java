package com.lackstreet.shared.adapter.in.rest.error;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;
import org.jboss.logging.MDC;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {

    private static final Logger LOG = Logger.getLogger(BadRequestExceptionMapper.class);

    @Override
    public Response toResponse(BadRequestException exception) {
        String request = (String) MDC.get("requestId");
        LOG.warn("Request failed: code=BAD_REQUEST");

        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(new ApiError(
                        "BAD_REQUEST",
                        "Richiesta non valida",
                        request
                ))
                .build();

    }
}
