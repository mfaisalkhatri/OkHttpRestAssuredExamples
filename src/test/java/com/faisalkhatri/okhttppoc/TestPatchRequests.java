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

import io.restassured.http.ContentType;
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
 * @since Mar 8, 2020
 */
public class TestPatchRequests {

    private static final String URL = "https://reqres.in";
    Logger log = LogManager.getLogger (TestPatchRequests.class);

    /**
     * @return putData
     *
     * @since Mar 8, 2020
     */
    @DataProvider (name = "patchData")
    public Iterator<Object[]> patchData () {
        final List<Object[]> patchData = new ArrayList<> ();
        patchData.add (new Object[] { 2, "Michael", "QA Lead" });
        patchData.add (new Object[] { 958, "Yuan", "Project Architect" });
        return patchData.iterator ();
    }

    /**
     * Executing Put Request using OkHttp
     *
     * @param id
     * @param name
     * @param job
     *
     * @throws IOException
     * @since Mar 8, 2020
     */
    @Test (dataProvider = "patchData", groups = "PatchTests")
    public void testPatchWithOkHttp (final int id, final String name, final String job) throws IOException {
        final MediaType JSON = MediaType.parse ("application/json; charset=utf-8");
        final PostData postData = new PostData (name, job);

        final OkHttpClient client = new OkHttpClient ();

        final JSONObject json = new JSONObject (postData);
        final RequestBody requestBody = RequestBody.create (json.toString (), JSON);

        final Request request = new Request.Builder ().url (URL + "/api/users/" + id)
            .addHeader ("Content-Type", "application/json;charset=utf-8")
            .header ("x-api-key", "reqres-free-v1")
            .patch (requestBody)
            .build ();

        final Response response = client.newCall (request)
            .execute ();

        final int statusCode = response.code ();
        this.log.info (statusCode);

        final String responseBody = response.body ()
            .string ();

        this.log.info (responseBody);

        final JSONObject jsonResponse = new JSONObject (responseBody);
        assertEquals (statusCode, 200);
        assertThat (jsonResponse.getString ("name"), equalTo (name));
        assertThat (jsonResponse.getString ("job"), equalTo (job));
    }

    /**
     * Executing Put Request using Rest Assured.
     *
     * @param id
     * @param name
     * @param job
     *
     * @since Mar 8, 2020
     */
    @Test (dataProvider = "patchData", groups = "PatchTests")
    public void testPatchWithRestAssured (final int id, final String name, final String job) {

        final PostData postData = new PostData (name, job);
        final String response = given ().contentType (ContentType.JSON)
            .header ("x-api-key", "reqres-free-v1")
            .body (postData)
            .when ()
            .patch (URL + "/api/users/" + id)
            .then ()
            .assertThat ()
            .statusCode (200)
            .and ()
            .assertThat ()
            .body ("name", equalTo (name))
            .and ()
            .assertThat ()
            .body ("job", equalTo (job))
            .and ()
            .extract ()
            .response ()
            .body ()
            .asString ();

        this.log.info (response);

    }
}