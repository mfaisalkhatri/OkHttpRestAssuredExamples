package com.faisalkhatri.okhttppoc;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @since Mar 8, 2020
 *
 */
public class TestDeleteRequests {

	Logger	log	= LogManager.getLogger (TestDeleteRequests.class);
	String	url	= "https://reqres.in/api/users/";

	/**
	 * @since Mar 8, 2020
	 * @return deleteUserData using okhttp
	 */
	@DataProvider (name = "deleteUserOkHttp")
	public Iterator <Object []> deleteokHttpUsers () {
		final List <Object []> deleteData = new ArrayList <> ();
		deleteData.add (new Object [] { 8 });
		return deleteData.iterator ();
	}

	/**
	 * @since Mar 8, 2020
	 * @return deleteUserData using rest assured
	 */
	@DataProvider (name = "deleteUserRestAssured")
	public Iterator <Object []> deleteRestUsers () {
		final List <Object []> deleteData = new ArrayList <> ();
		deleteData.add (new Object [] { 2 });
		return deleteData.iterator ();
	}

	/**
	 * Executing delete request using okhttp
	 *
	 * @since Mar 8, 2020
	 * @param userId
	 * @throws IOException
	 *
	 */
	@Test (dataProvider = "deleteUserOkHttp")
	public void testDeleteUsingOkHttp (final int userId) throws IOException {
		final OkHttpClient client = new OkHttpClient ();
		final Request request = new Request.Builder ().url (this.url + userId)
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
	 *
	 *
	 */
	@Test (dataProvider = "deleteUserRestAssured")
	public void testDeleUsingRestAsured (final int userId) {
		given ().when ()
			.delete (this.url + userId)
			.then ()
			.assertThat ()
			.statusCode (204);
	}

}
