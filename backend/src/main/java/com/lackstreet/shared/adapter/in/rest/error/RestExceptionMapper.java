package com.lackstreet.shared.adapter.in.rest.error;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.MDC;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

public class RestExceptionMapper {
    @ServerExceptionMapper
    public Response handleIllegalArgument(IllegalArgumentException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(ProblemDetail.badRequest(
                        (String) MDC.get("requestId"),
                        e.getMessage()
                ))
                .build();
    }
}
