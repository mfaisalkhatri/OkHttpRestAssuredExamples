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
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @since Mar 8, 2020
 */
public class TestDeleteRequests {

    private static final String URL = "https://reqres.in/api/users/";

    /**
     * @since Mar 8, 2020
     * @return deleteUserData using okhttp
     */
    @DataProvider (name = "deleteUserOkHttp")
    public Iterator<Object []> deleteokHttpUsers () {
        final List<Object []> deleteData = new ArrayList<> ();
        deleteData.add (new Object [] { 8 });
        return deleteData.iterator ();
    }

    /**
     * @since Mar 8, 2020
     * @return deleteUserData using rest assured
     */
    @DataProvider (name = "deleteUserRestAssured")
    public Iterator<Object []> deleteRestUsers () {
        final List<Object []> deleteData = new ArrayList<> ();
        deleteData.add (new Object [] { 2 });
        return deleteData.iterator ();
    }

    /**
     * Executing delete request using okhttp
     *
     * @since Mar 8, 2020
     * @param userId
     * @throws IOException
     */
    @Test (dataProvider = "deleteUserOkHttp", groups = "DeleteTests")
    public void testDeleteUsingOkHttp (final int userId) throws IOException {
        final OkHttpClient client = new OkHttpClient ();
        final Request request = new Request.Builder ().url (URL + userId)
            .delete ()
            .build ();

        final Response response = client.newCall (request)
            .execute ();
        final int statusCode = response.code ();
        assertEquals (statusCode, 204);
    }

    /**
     * Executing delete requests using Rest-assured
     *
     * @since Mar 08, 2020
     * @param userId
     */
    @Test (dataProvider = "deleteUserRestAssured", groups = "DeleteTests")
    public void testDeleUsingRestAsured (final int userId) {
        given ().when ()
            .delete (URL + userId)
            .then ()
            .assertThat ()
            .statusCode (204);
    }

}