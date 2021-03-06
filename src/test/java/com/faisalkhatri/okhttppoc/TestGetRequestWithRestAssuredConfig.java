package com.faisalkhatri.okhttppoc;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestGetRequestWithRestAssuredConfig extends SetupConfig {

    @Test(groups = "GetTests")
    public void testGetRequestwithRestAssured() {
        given()
                .when()
                .get("/api/users/2")
                .then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("data.first_name", equalTo("Janet"));
    }
}
