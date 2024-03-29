/*
        Copyright (c) 2022 Mohammad Faisal Khatri

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
*/

package com.faisalkhatri.okhttppoc;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.github.javafaker.Faker;
import data.UserData;
import org.testng.annotations.Test;


public class TestPostRequestBuilderExample extends SetupConfig {


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