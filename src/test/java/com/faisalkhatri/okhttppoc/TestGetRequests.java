package com.faisalkhatri.okhttppoc;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @since Mar 7, 2020
 *
 */
public class TestGetRequests {

	Logger	log	= LogManager.getLogger (TestGetRequests.class);
	String	url	= "https://reqres.in/api/users/";

	/**
	 * @since Mar 7, 2020
	 * @return getUserData
	 */
	@DataProvider (name = "getUserData")
	public Iterator <Object []> getUsers () {
		final List <Object []> getData = new ArrayList <> ();
		getData.add (new Object [] { 2 });
		return getData.iterator ();
	}

	/**
	 * Executing get request using okhttp
	 *
	 * @param userId
	 * @since Mar 7, 2020
	 * @throws IOException
	 *
	 */
	@Test (dataProvider = "getUserData", groups = "GetTests")
	public void testGetRequestWithOkHttp (final int userId) throws IOException {
		final OkHttpClient client = new OkHttpClient ();
		final Request request = new Request.Builder ().url (this.url + userId)
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
	 * Executing get requests using Rest-assured
	 *
	 * @param userId
	 *
	 * @since Mar 7, 2020
	 */
	@Test (dataProvider = "getUserData", groups = "GetTests")
	public void testGetRequestWithRestAssured (final int userId) {
		given ().when ()
			.get (this.url + userId)
			.then ()
			.statusCode (200)
			.and ()
			.assertThat ()
			.body ("data.id", equalTo (userId));

		final int statusCode = given ().when ()
			.get (this.url + userId)
			.statusCode ();
		this.log.info (statusCode);

		final String responseBody = given ().when ()
			.get (this.url + userId)
			.getBody ()
			.asString ();
		this.log.info (responseBody);
	}

	/**
	 * @since Mar 7, 2020
	 * @param userPage
	 */
	@Test (dataProvider = "getUserData", groups = "GetTests")
	public void testGetRequestWithQueryParamWithRestAssured (final int userPage) {
		given ().when ()
			.queryParam ("page", userPage)
			.get (this.url)
			.then ()
			.statusCode (200)
			.and ()
			.assertThat ()
			.body ("page", equalTo (userPage));

		final String responseBody = given ().when ()
			.queryParam ("page", userPage)
			.get (this.url)
			.getBody ()
			.asString ();
		this.log.info (responseBody);
	}

	/**
	 * @since Mar 7, 2020
	 * @param userPage
	 * @throws IOException
	 */
	@Test (dataProvider = "getUserData", groups = "GetTests")
	public void testGetRequestWithQueryParamOkHttp (final int userPage) throws IOException {
		final OkHttpClient client = new OkHttpClient ();
		final HttpUrl.Builder urlBuilder = HttpUrl.parse (this.url)
			.newBuilder ();
		urlBuilder.addQueryParameter ("page", String.valueOf (userPage));

		final String currentUrl = urlBuilder.build ()
			.toString ();
		final Request request = new Request.Builder ().url (currentUrl)
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
}
