package com.faisalkhatri.okhttppoc;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.github.javafaker.Faker;
import data.UserData;
import org.testng.annotations.Test;


public class PostRequestBuilderExample extends SetupConfig {


    @Test
    public void postUsingBuilderPatternWithRestAssured() {

        UserData userData = userDataBuilder();
        given()
                .body(userData)
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .and()
                .assertThat()
                .body("name", equalTo(userData.getName()))
                .body("job", equalTo(userData.getJob()));

    }

    private UserData userDataBuilder() {
        Faker faker = Faker.instance();
        return UserData.builder().name(faker.name().firstName()).job(faker.company().profession())
                .build();
    }


}
