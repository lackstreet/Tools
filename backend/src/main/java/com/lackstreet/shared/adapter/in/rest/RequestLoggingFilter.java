package com.lackstreet.shared.adapter.in.rest;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;
import org.jboss.logging.MDC;

import java.io.IOException;
import java.util.UUID;

@Provider
public class RequestLoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {
   private static final Logger LOG = Logger.getLogger(RequestLoggingFilter.class.getName());
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String requestId = UUID.randomUUID().toString();
        requestContext.setProperty("requestId", requestId);
        MDC.put("requestId", requestId);

        LOG.debugf("Richiesta ricevuta: method=%s path=%s",
                requestContext.getMethod(),
                requestContext.getUriInfo().getPath());

            MDC.put("requestId", requestId);

    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {

        try {
            Object requestId = requestContext.getProperty("requestId");

            if (requestId != null) {
                responseContext.getHeaders()
                        .putSingle("X-Request-ID", requestId);
            }

            LOG.debugf("Risposta: method=%s status=%d",
                    requestContext.getMethod(),
                    responseContext.getStatus());
        } finally {
            MDC.remove("requestId");
        }

    }
}
