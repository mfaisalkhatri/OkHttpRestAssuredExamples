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
import static org.junit.Assert.assertThat;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @since Mar 7, 2020
 */
public class TestGetRequests {

    private static final String URL = "https://reqres.in/api/users/";
    Logger log = LogManager.getLogger (TestGetRequests.class);

    /**
     * @return getUserData
     *
     * @since Mar 7, 2020
     */
    @DataProvider (name = "getUserData")
    public Iterator<Object[]> getUsers () {
        final List<Object[]> getData = new ArrayList<> ();
        getData.add (new Object[] { 2 });
        return getData.iterator ();
    }

    /**
     * Executing get request using okhttp
     *
     * @param userId
     *
     * @throws IOException
     * @since Mar 7, 2020
     */
    @Test (dataProvider = "getUserData", groups = "GetTests")
    public void testGetRequestWithOkHttp (final int userId) throws IOException {
        final OkHttpClient client = new OkHttpClient ();
        final Request request = new Request.Builder ().url (URL + userId)
            .get ()
            .build ();

        final Response response = client.newCall (request)
            .execute ();
        final String responseBody = response.body ()
            .string ();
        final int statusCode = response.code ();
        this.log.info (responseBody);

        assertEquals (statusCode, 200);

        final JSONObject jsonResponse = new JSONObject (responseBody);
        final int id = jsonResponse.getJSONObject ("data")
            .getInt ("id");
        assertThat (id, equalTo (userId));
    }

    /**
     * @param userPage
     *
     * @throws IOException
     * @since Mar 7, 2020
     */
    @Test (dataProvider = "getUserData", groups = "GetTests")
    public void testGetRequestWithQueryParamOkHttp (final int userPage) throws IOException {
        final OkHttpClient client = new OkHttpClient ();
        final HttpUrl.Builder urlBuilder = HttpUrl.parse (URL)
            .newBuilder ();
        urlBuilder.addQueryParameter ("page", String.valueOf (userPage));

        final String currentUrl = urlBuilder.build ()
            .toString ();
        final Request request = new Request.Builder ().url (currentUrl).header ("x-api-key", "reqres-free-v1")
            .build ();
        final Response response = client.newCall (request)
            .execute ();
        final String responseBody = response.body ()
            .string ();
        final int statusCode = response.code ();
        this.log.info (statusCode);
        this.log.info (responseBody);
        assertEquals (statusCode, 200);
        final JSONObject jsonResponse = new JSONObject (responseBody);
        assertThat (jsonResponse.getInt ("page"), equalTo (userPage));
        final String firstName = jsonResponse.getJSONArray ("data")
            .getJSONObject (0)
            .getString ("first_name");
        assertThat (firstName, equalTo ("Michael"));

    }

    /**
     * @param userPage
     *
     * @since Mar 7, 2020
     */
    @Test (dataProvider = "getUserData", groups = "GetTests")
    public void testGetRequestWithQueryParamWithRestAssured (final int userPage) {
        given ().when ()
            .header ("x-api-key", "reqres-free-v1")
            .queryParam ("page", userPage)
            .get (URL)
            .then ()
            .statusCode (200)
            .and ()
            .assertThat ()
            .body ("page", equalTo (userPage));

        final String responseBody = given ().when ()
            .queryParam ("page", userPage)
            .get (URL)
            .getBody ()
            .asString ();
        this.log.info (responseBody);
    }

    /**
     * Executing get requests using Rest-assured
     *
     * @param userId
     *
     * @since Mar 7, 2020
     */
    @Test (dataProvider = "getUserData", groups = "GetTests")
    public void testGetRequestWithRestAssured (final int userId) {
        given ().when ()
            .header ("x-api-key", "reqres-free-v1")
            .get (URL + userId)
            .then ()
            .statusCode (200)
            .and ()
            .assertThat ()
            .body ("data.id", equalTo (userId));

        final int statusCode = given ().when ()
            .get (URL + userId)
            .statusCode ();
        this.log.info (statusCode);

        final String responseBody = given ().when ()
            .get (URL + userId)
            .getBody ()
            .asString ();
        this.log.info (responseBody);
    }
}