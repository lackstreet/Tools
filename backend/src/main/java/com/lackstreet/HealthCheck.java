package com.lackstreet;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("api/healthcheck")
public class HealthCheck {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String isAlive() {
        return "PDF Tools è attivo";
    }
}
