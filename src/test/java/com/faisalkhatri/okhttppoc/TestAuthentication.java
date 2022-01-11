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
import static org.hamcrest.Matchers.notNullValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Faisal Khatri
 * @since Aug 2, 2020
 */
public class TestAuthentication {

    private static final String URL = "https://reqres.in";

    /**
     * @author Faisal Khatri
     * @since Aug 2, 2020
     * @return test data
     */
    @DataProvider
    public Iterator<Object []> getAuthenticationData () {
        final List<Object []> getTestData = new ArrayList<> ();
        getTestData.add (new Object [] { "eve.holt@reqres.in", "pistol" });
        return getTestData.iterator ();
    }

    /**
     * @author Faisal Khatri
     * @since Aug 2, 2020
     * @param email
     * @param password
     */
    @Test (dataProvider = "getAuthenticationData")
    public void testAuthenticationToken (String email, String password) {
        final AuthenticationPojo requestBody = new AuthenticationPojo (email, password);

        given ().contentType (ContentType.JSON)
            .body (requestBody)
            .when ()
            .log ()
            .all ()
            .post (URL + "/api/register")
            .then ()
            .assertThat ()
            .statusCode (200)
            .log ()
            .all ()
            .body ("id", notNullValue ())
            .and ()
            .body ("token", notNullValue ());

    }

    /**
     * @author Faisal Khatri
     * @since Aug 2, 2020
     * @param email
     * @param password
     * @return auth details
     */
    public static Map<String, Object> getToken (String email, String password) {
        final AuthenticationPojo requestBody = new AuthenticationPojo (email, password);
        final String response = given ().contentType (ContentType.JSON)
            .body (requestBody)
            .when ()
            .log ()
            .all ()
            .post (URL + "/api/register")
            .then ()
            .assertThat ()
            .statusCode (200)
            .log ()
            .all ()
            .body ("id", notNullValue ())
            .and ()
            .body ("token", notNullValue ())
            .and ()
            .extract ()
            .response ()
            .asString ();

        final JSONObject responseObject = new JSONObject (response);
        final Map<String, Object> responseMap = new HashMap<> ();
        responseMap.put ("id", responseObject.getInt ("id"));
        responseMap.put ("token", responseObject.getString ("token"));
        return responseMap;
    }

    /**
     * @author Faisal Khatri
     * @since Aug 2, 2020
     * @param email
     * @param password
     */
    @Test (dataProvider = "getAuthenticationData")
    public void testAuthToken (String email, String password) {
        System.out.println ("Token is" + getToken (email, password).get ("token")
            .toString ());

    }
}