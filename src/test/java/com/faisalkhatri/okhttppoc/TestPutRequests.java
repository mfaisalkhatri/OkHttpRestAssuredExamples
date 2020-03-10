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

import io.restassured.http.ContentType;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @since Mar 8, 2020
 *
 */
public class TestPutRequests {

	Logger					log	= LogManager.getLogger (TestPutRequests.class);
	private final String	url	= "https://reqres.in";

	/**
	 * @since Mar 8, 2020
	 * @return putData
	 */
	@DataProvider (name = "putData")
	public Iterator <Object []> putData () {
		final List <Object []> putData = new ArrayList <> ();
		putData.add (new Object [] { 2, "Michael", "QA Lead" });
		putData.add (new Object [] { 958, "Yuan", "Project Architect" });
		return putData.iterator ();
	}

	/**
	 * Executing Put Request using Rest Assured.
	 *
	 * @since Mar 8, 2020
	 * @param id
	 * @param name
	 * @param job
	 */
	@Test (dataProvider = "putData", groups = "PutTests")
	public void testPutWithRestAssured (final int id, final String name, final String job) {

		final PostData postData = new PostData (name, job);
		final String response = given ().contentType (ContentType.JSON)
			.body (postData)
			.when ()
			.put (this.url + "/api/users/" + id)
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

	/**
	 * Executing Put Request using OkHttp
	 *
	 * @since Mar 8, 2020
	 * @param id
	 * @param name
	 * @param job
	 * @throws IOException
	 *
	 */
	@Test (dataProvider = "putData", groups = "PutTests")
	public void testPutWithOkHttp (final int id, final String name, final String job)
		throws IOException {
		final MediaType JSON = MediaType.parse ("application/json; charset=utf-8");
		final PostData postData = new PostData (name, job);

		final OkHttpClient client = new OkHttpClient ();

		final JSONObject json = new JSONObject (postData);
		final RequestBody requestBody = RequestBody.create (json.toString (), JSON);

		final Request request = new Request.Builder ().url (this.url + "/api/users" + id)
			.addHeader ("Content-Type", "application/json;charset=utf-8")
			.put (requestBody)
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

}
