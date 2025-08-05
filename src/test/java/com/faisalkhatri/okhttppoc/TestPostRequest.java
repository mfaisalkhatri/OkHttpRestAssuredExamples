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
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.restassured.http.ContentType;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @since Mar 7, 2020
 */
public class TestPostRequest {

    private static final String URL = "https://reqres.in";
    Logger log = LogManager.getLogger (TestPostRequest.class);

    /**
     * @return postData
     *
     * @since Mar 7, 2020
     */
    @DataProvider (name = "postData")
    public Iterator<Object[]> postData () {
        final List<Object[]> postData = new ArrayList<> ();
        postData.add (new Object[] { "Rahul", "QA" });
        postData.add (new Object[] { "Jane", "Sr.Dev" });
        postData.add (new Object[] { "Albert", "Dev" });
        postData.add (new Object[] { "Johnny", "Project Manager" });
        return postData.iterator ();
    }

    /**
     * @param name
     * @param job
     *
     * @throws IOException
     * @since Mar 7, 2020
     */
    @Test (dataProvider = "postData", groups = "PostTests")
    public void testPostWithOkHttp (final String name, final String job) throws IOException {
        final MediaType JSON = MediaType.parse ("application/json; charset=utf-8");
        final PostData postData = new PostData (name, job);

        final OkHttpClient client = new OkHttpClient ();

        final JSONObject json = new JSONObject (postData);
        final RequestBody requestBody = RequestBody.create (json.toString (), JSON);

        final Request request = new Request.Builder ().url (URL + "/api/users")
            .addHeader ("Content-Type", "application/json;charset=utf-8")
            .post (requestBody)
            .build ();

        final Response response = client.newCall (request)
            .execute ();

        final int statusCode = response.code ();
        this.log.info (statusCode);

        final String responseBody = response.body ()
            .string ();

        this.log.info (responseBody);

        final JSONObject jsonResponse = new JSONObject (responseBody);
        assertEquals (statusCode, 201);
        assertThat (jsonResponse.getString ("name"), equalTo (name));
        assertThat (jsonResponse.getString ("job"), equalTo (job));
        assertThat (jsonResponse.getString ("id"), notNullValue ());

    }

    /**
     * @param name
     * @param job
     *
     * @since Mar 7, 2020
     */
    @Test (dataProvider = "postData", groups = "PostTests")
    public void testPostWithRestAssured (final String name, final String job) {
        final PostData postData = new PostData (name, job);
        final String response = given ().contentType (ContentType.JSON)
            .body (postData)
            .when ()
            .post (URL + "/api/users")
            .then ()
            .assertThat ()
            .statusCode (201)
            .and ()
            .assertThat ()
            .body ("name", equalTo (name))
            .and ()
            .assertThat ()
            .body ("job", equalTo (job))
            .and ()
            .assertThat ()
            .body ("id", notNullValue ())
            .and ()
            .extract ()
            .response ()
            .body ()
            .asString ();

        this.log.info (response);

    }

    /**
     * @param name
     * @param job
     *
     * @throws IOException
     * @since Mar 7, 2020
     */
    @Test (dataProvider = "postData", groups = "PostTests")
    public void testPostwithOkHttpForm (final String name, final String job) throws IOException {
        final OkHttpClient client = new OkHttpClient ();
        final RequestBody formBody = new FormBody.Builder ().add ("name", name)
            .add ("job", job)
            .build ();
        final Request request = new Request.Builder ().url (URL + "/api/users")
            .post (formBody)
            .build ();

        final Response response = client.newCall (request)
            .execute ();

        final int statusCode = response.code ();
        this.log.info (statusCode);

        final String responseBody = response.body ()
            .string ();

        this.log.info (responseBody);

        final JSONObject jsonResponse = new JSONObject (responseBody);
        assertEquals (statusCode, 201);
        assertThat (jsonResponse.getString ("name"), equalTo (name));
        assertThat (jsonResponse.getString ("job"), equalTo (job));
        assertThat (jsonResponse.getString ("id"), notNullValue ());

    }
}