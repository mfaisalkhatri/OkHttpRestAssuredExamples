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
        getToken (email, password).get ("token");

    }
}
