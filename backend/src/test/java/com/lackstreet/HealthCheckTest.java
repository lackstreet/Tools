package com.lackstreet;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
class HealthCheckTest {
    @Test
    void testIsAliveEndpoint(){
                 given()
                .when()
                .get("api/healthcheck")
                .then()
                .statusCode(200)
                .body(is("PDF Tools è attivo"));
    }
}
