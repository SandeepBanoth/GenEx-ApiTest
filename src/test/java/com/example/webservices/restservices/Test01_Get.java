package com.example.webservices.restservices;

import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.authentication.BasicAuthScheme;
import io.restassured.authentication.OAuth2Scheme;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class Test01_Get {

	@Test
	void test_01() {

		String username = "username";
		String password = "1234";

		/* Response response = */

		given().auth().basic(username, password);

		RestAssured.get("http://localhost:8080/userr")/* response */.then().log().all().
				/* assertThat(). */statusCode(200).body(containsString("Sandeep"));

	}

	@Test
	void postTesting() {

		String content = "{\r\n" + "        \"id\": 6,\r\n" + "        \"birthDate\": \"1998-09-13\",\r\n"
				+ "        \"person_name\": \"Sandeep6\"\r\n" + "    }";

		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		given().log().all().header("Content-", "application/json").body(content).when().post("/users").then().log()
				.all().assertThat().statusCode(201);

	}

	@Test
	public void test_NumberOfCircuits() {

		given().when().get("http://ergast.com/api/f1/2017/circuits.json").then().log().all().assertThat()
				.body("MRData.CircuitTable.Circuits.circuitId", hasSize(20));
	}

	@Test
	public void test_ResponseHeader() {

		given().when().log().all().get("http://ergast.com/api/f1/2017/circuits.json").then()
				/* .assertThat() */.statusCode(200).and().contentType(ContentType.JSON).and()
				.header("Content-Length", equalTo("4552"));
	}

	/*
	 * @Test public void test_NumberOfCircuits_ShouldBe20_UsingResponseSpec() {
	 *
	 * given(). when(). get("http://ergast.com/api/f1/2017/circuits.json").
	 * then().log().all(). assertThat(). spec(checkStatusCodeAndContentType). and().
	 * body("MRData.CircuitTable.Circuits.CircuitId", hasSize(20)); }
	 */

	@Test
	public void test_ScenarioRetrieveFirstCircuitFor2017SeasonAndGetCountry_ShouldBeAustralia() {

		// First, retrieve the circuit ID for the first circuit of the 2017 season
		String circuitId = given().log().all().when().get("http://ergast.com/api/f1/2017/circuits.json").then()
				.extract().path("MRData.CircuitTable.Circuits.circuitId[0]");

		// Then, retrieve the information known for that circuit and verify it is
		// located in Australia
		given().pathParam("circuitId", circuitId).when().get("http://ergast.com/api/f1/circuits/{circuitId}.json")
				.then().assertThat().body("MRData.CircuitTable.Circuits.Location[0].country", equalTo("Australia"));
	}

	/*
	 * @Test public void test() {
	 *
	 * Response response = RestAssured.get("https://reqres.in/api/users?page=2");
	 * System.out.println(response.statusCode());
	 * System.out.println(response.asString());
	 * System.out.println(response.getBody().asString());
	 * System.out.println(response.statusLine());
	 *
	 * int statusCode = response.getStatusCode(); Assert.assertEquals(statusCode,
	 * 200);
	 *
	 * }
	 *
	 * @Test public void test1() {
	 *
	 * given().get("https://reqres.in/api/users?page=2").then().statusCode(200).body
	 * ("data.id[0]", equalTo(7));
	 *
	 * }
	 */

	@Test
	public void test1() {

		given().get("https://reqres.in/api/users?page=2").then().statusCode(200).body("data.id[1]", equalTo(8))
				.body("data.first_name", hasItems("Michael", "Lindsay")).log().all();

	}

	/*
	 * @BeforeAll public static void setup() { RestAssured.baseURI =
	 * "https://jsonplaceholder.typicode.com"; }
	 */

	@Test
	public void getRequest() {
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

		String username = "username";
		String password = "1234";

		Response response = given().log().all().auth().basic(username, password)
				/*
				 * oauth("consumerKey", "consumerSecret", "accessToken", "secretToken")
				 */
				.contentType(ContentType.JSON).when().get("/posts").then().assertThat()
				.body("title", hasItems("qui est esse")).statusCode(200).extract().response();

		Assertions.assertEquals(200, response.statusCode());
		/*
		 * Assertions.assertEquals("qui est esse",
		 * response.jsonPath().getString("title[1]"));
		 */

	}

	@Test
	void test03() {

		given().baseUri("https://reqres.in/").when().get("api/users?page=2").then().assertThat()
				.body("$", hasKey("page")).body("$", hasKey("total")).statusCode(200);

	}

	/*
	 * @Test
	 *
	 * public void testGetUsersWithAuth() {
	 *
	 * // Set the base URL
	 *
	 * RestAssured.baseURI = "http://localhost:8080"; // Replace with your actual
	 * base URL
	 *
	 * // Define basic authentication credentials
	 *
	 * String username = "your_username";
	 *
	 * String password = "your_password"; // Send a GET request to /users with JSON
	 * content type and basic authentication
	 *
	 * Response response = (Response) given()
	 *
	 * .auth()
	 *
	 * .basic(username, password)
	 *
	 * .contentType(ContentType.JSON)
	 *
	 * .get("/users");
	 *
	 * // Assert the status code is 200
	 *
	 * assertEquals(200, response.getStatusCode());
	 *
	 * // Assert that the response body has a key named "page"
	 *
	 *
	 *
	 * assertTrue(response.getBody().jsonPath().getMap("").containsKey("page"));
	 *
	 *
	 * }
	 */

	/*
	 * @Test
	 *
	 * public void testGetUsersWithAuth() {
	 *
	 * // Set the base URL
	 *
	 * RestAssured.baseURI = "http://localhost:8080"; // Replace with your actual
	 * base URL
	 *
	 *
	 *
	 * // Define basic authentication credentials
	 *
	 * String username = "Sandeep";
	 *
	 * String password = "12345";
	 *
	 *
	 *
	 * // Send a GET request to /users with JSON content type and basic
	 * authentication
	 *
	 * given()
	 *
	 * .log()
	 *
	 * .all()
	 *
	 * .auth()
	 *
	 * .basic(username, password)
	 *
	 * .contentType(ContentType.JSON)
	 *
	 * .when()
	 *
	 * .get("/users")
	 *
	 * .then()
	 *
	 * .statusCode(200)
	 *
	 * .body("birthDate",is(notNullValue())).body("id",hasItem(3)); // Check if
	 * "data" has a key named "birthDate"
	 *
	 * }
	 *
	 * }
	 */

	/*
	 * @BeforeTest
	 *
	 * public void setUp() {
	 *
	 * RestAssured.baseURI = "http://localhost:8080";
	 *
	 *
	 *
	 * // Basic Authentication
	 *
	 * PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
	 *
	 * authScheme.setUserName("yourUsername");
	 *
	 * authScheme.setPassword("yourPassword");
	 *
	 * RestAssured.authentication = authScheme;
	 *
	 * }
	 *
	 *
	 *
	 * @Test
	 *
	 * public void getUsersTest() {
	 *
	 * Response response =
	 *
	 * given()
	 *
	 * .contentType("application/json")
	 *
	 * .when()
	 *
	 * .get("/users")
	 *
	 * .then()
	 *
	 * .statusCode(200)
	 *
	 * .body("birthdate", notNullValue())
	 *
	 * .body("Id", hasSize(3))
	 *
	 * .extract()
	 *
	 * .response();
	 *
	 *
	 *
	 * // You can further assert or validate other aspects of the response if
	 * needed.
	 *
	 * }
	 *
	 * }
	 */

	@Test

	public void getUsersTest() {

		// Basic Authentication

		PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();

		authScheme.setUserName("yourUsername");

		authScheme.setPassword("yourPassword");

		RestAssured.authentication = authScheme;

		Response response =

				given()

						.contentType("application/json")

						.when()

						.get("http://localhost:8080/users")

						.then()

						.statusCode(200)

						.body("birthdate", notNullValue())

						.body("Id", hasItem(3))

						.extract()

						.response();

		// You can further assert or validate other aspects of the response if needed.

	}

	/*
	 * @Test
	 *
	 * public void testGetUsers() {
	 *
	 * // Set the base URL to https://reqres.in, a public testing API
	 *
	 * RestAssured.baseURI = "https://reqres.in";
	 *
	 *
	 *
	 * // Send a GET request to retrieve a list of users
	 *
	 * Response response = RestAssured.given()
	 *
	 * .header("Content-Type", "application/json")
	 *
	 * .get("/api/users");
	 *
	 *
	 *
	 * // Get the HTTP status code from the response
	 *
	 * int statusCode = response.getStatusCode();
	 *
	 *
	 *
	 * // Assert that the status code is 200 (OK)
	 *
	 * assertEquals(200, statusCode, "Expected status code 200");
	 *
	 *
	 *
	 * // You can also check the response body, headers, and more if needed
	 *
	 * String responseBody = response.getBody().asString();
	 *
	 * System.out.println("Response Body: " + responseBody);
	 *
	 *
	 *
	 * // Add more assertions or further processing based on the response
	 *
	 * }
	 */

	/*
	 * @Test
	 *
	 * public void testGetUsers() {
	 *
	 * // Set the base URL to https://reqres.in, a public testing API
	 *
	 * RestAssured.baseURI = "https://reqres.in";
	 *
	 *
	 *
	 * // Send a GET request to retrieve a list of users
	 *
	 * Response response = RestAssured.given()
	 *
	 * .header("Content-Type", "application/json")
	 *
	 * .get("/api/users");
	 *
	 *
	 *
	 * // Get the HTTP status code from the response
	 *
	 * int statusCode = response.getStatusCode();
	 *
	 *
	 *
	 * // Assert that the status code is 200 (OK)
	 *
	 * assertEquals(200, statusCode, "Expected status code 200");
	 *
	 *
	 *
	 * // Get the response body as a JsonPath object for easier JSON parsing
	 *
	 * JsonPath jsonPath = response.jsonPath();
	 *
	 *
	 *
	 * // Check the size of specific fields
	 *
	 * int numberOfUsers = jsonPath.getList("data").size();
	 *
	 * assertEquals(6, numberOfUsers, "Expected 6 users in the response");
	 *
	 *
	 *
	 * // Check if specific items are present in the 'email' field
	 *
	 * assertTrue(jsonPath.getList("data.email",
	 * String.class).contains("george.bluth@reqres.in"));
	 *
	 * assertTrue(jsonPath.getList("data.email",
	 * String.class).contains("janet.weaver@reqres.in"));
	 *
	 *
	 *
	 * // Add more assertions or further processing as needed
	 *
	 * }
	 */

	@Test

	public void testGetUsers() {

		RestAssured.baseURI = "https://reqres.in";

		Response response = RestAssured.given().log().all()

				.header("Content-Type", "application/xml")

				.get("/api/users");

		// Get the HTTP status code from the response

		int statusCode = response.getStatusCode();

		// Assert that the status code is 200 (OK)

		assertEquals(200, statusCode, "Expected status code 200");

		// Check the size of the 'data' field

		response.then().assertThat()

				.body("data.size()", equalTo(6));

		// Check if specific items are present in the 'email' field

		response.then().assertThat()

				.body("data.email", hasItems("george.bluth@reqres.in", "janet.weaver@reqres.in"));

	}

	@Test

	public void testCreateUser() {

		RestAssured.baseURI = "https://reqres.in";

		// Define the request body as a string in JSON format

		String requestBody = "{ \"name\": \"John\", \"job\": \"Tester\" }";

		// Send a POST request to create a new user

		Response response = RestAssured.given().log().all()

				.header("Content-Type", "application/json")

				.body(requestBody)

				.post("/api/users");

		// Get the HTTP status code from the response

		int statusCode = response.getStatusCode();

		// Assert that the status code is 201 (Created)

		assertEquals(201, statusCode, "Expected status code 201");

		// You can also check the response body, headers, and more if needed

		String responseBody = response.getBody().asString();

		System.out.println("Response Body: " + responseBody);

		// Add more assertions or further processing as needed

	}

	@Test

	public void testCreateUserWithQueryParams() {

		RestAssured.baseURI = "https://reqres.in";

		String name = "John";

		String job = "Tester";

		Response response = RestAssured.given()

				.header("Content-Type", "application/json")

				.queryParam("name", name, "job", job)

				/*.queryParam("job", job)*/

				.post("/api/users/1");

		int statusCode = response.getStatusCode();

		assertEquals(201, statusCode, "Expected status code 201");

		String responseBody = response.getBody().asString();

		System.out.println("Response Body: " + responseBody + " name : " + name + " Job : " + job + " Status Code : " + statusCode);

	}

	@Test
	public void validateAPIEndpoint() {
		// Set the Base URI for the API
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";


		// Set Basic Authentication credentials
		BasicAuthScheme authScheme = new BasicAuthScheme();
		authScheme.setUserName("username");
		authScheme.setPassword("password");
		RestAssured.authentication = authScheme;


		// Make a GET request to the endpoint "/posts"
		given()
				.when()
				.get("/posts")
				.then()
				.assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body("title", hasItems("qui est esse"));
	}


	/*@Test
	public void fileUploadTest() {
		given()
				.param("timestamp", new Date().getTime())
				.multiPart("file", new File("\"C:\\Users\\sandeep_banoth\\Downloads\\Iron Man.jpg\""))
				.when()
				.post("http://localhost:8080/fileupload")
				.then()
				.statusCode(200)
				.body("success", equalTo(true));
	}*/

	private static String requestBody = "{\n" +
			"  \"title\": \"foo\",\n" +
			"  \"body\": \"bar\",\n" +
			"  \"userId\": \"1\" \n}";


	@Test
	public void postRequest() {
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
		Response response = given().log().all()
				.header("Content-type", "application/json")
				.and()
				.body(requestBody)
				.when()
				.post("/posts")
				.then()
				.extract().response();

		Assertions.assertEquals(201, response.statusCode());
		Assertions.assertEquals("foo", response.jsonPath().getString("title"));
		Assertions.assertEquals("bar", response.jsonPath().getString("body"));
		Assertions.assertEquals("1", response.jsonPath().getString("userId"));
		Assertions.assertEquals("101", response.jsonPath().getString("id"));
	}


	@Test

	/*public void uploadFile() {

		File testUploadFile = new File("C:\\Users\\sandeep_banoth\\Downloads\\Iron Man.jpg\""); //Specify your own location and file

		RestAssured.baseURI = "http://localhost:8080";

		Response response = given()

				.multiPart(testUploadFile)

				.when().

				post("/single-file-upload");



		System.out.println(response.getStatusCode());

		System.out.println(response.asString());

		assertTrue(response.asString().contains("successfully uploaded"));

	}*/


	public void delete() {
		// Set the Base URI for the API
		RestAssured.baseURI = "https://reqres.in/api";


		// Make a delete request to the endpoint "/users/2"
		Response response = RestAssured.delete("/users/2");


		// Assert the status code
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 204);


		// Print the response
		System.out.println(response.asString());
	}

	@Test
	public void validateAPIEndpoint01() {
		// Set the Base URI for the API
		RestAssured.baseURI = "https://reqres.in/api";


		// Request body
		String requestBody = "{\n" +
				" \"name\": \"Sam\",\n" +
				" \"job\": \"Project Leader\"\n" +
				"}";


		// Make a post request to the endpoint "/users"
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(requestBody)
				.post("/users");


		// Assert the status code
		Assert.assertEquals(response.getStatusCode(), 201);


		// Assert the response body
		Assert.assertEquals(response.jsonPath().getString("name"), "Sam");
		Assert.assertEquals(response.jsonPath().getString("job"), "Project Leader");
	}


	@Test
	public void pathParams() {
		// Set the Base URI for the API
		RestAssured.baseURI = "https://reqres.in/api/users";


		// Make a GET request to the endpoint with path parameter id=3
		Response response = RestAssured.given().pathParam("id", 3).get("/{id}");


		// Assert the status code is 200
		Assert.assertEquals(response.getStatusCode(), 200);


		// Assert the value of data.last_name is 'Wong'
		String lastName = response.jsonPath().getString("data.last_name");
		Assert.assertEquals(lastName, "Wong");


		// Print the response
		System.out.println(response.getBody().asString());
	}

	@Test

	public void deleteUser() {

		// Define the base URL for the ReqRes API

		RestAssured.baseURI = "https://reqres.in/api";


		// Define the user ID you want to delete

		int userId = 2;


		// Send a DELETE request to delete the user

		given()

				.when().log().all()

				.delete("/users/" + userId)

				.then()

				.statusCode(204); // Expecting a 204 No Content response for successful deletion


	}


	@Test
	public void validateAPIEndpoint02() {
		// Set the Base URI for the API
		RestAssured.baseURI = "https://reqres.in/api";


		// Request body
		String requestBody = "{\n" +
				" \"name\": \"Adam\",\n" +
				" \"job\": \"Software Engineer\"\n" +
				"}";


		// Make a PUT request to the endpoint /users/2
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(requestBody)
				.put("/users/2");


		// Assert the status code is 200
		Assert.assertEquals(response.getStatusCode(), 200);


		// Assert the job field contains 'Software'
		String job = response.jsonPath().getString("job");
		Assert.assertTrue(job.contains("Software"));
	}

	@Test
	public void validateAPIEndpoint03() {
		// Set the Base URI for the API
		RestAssured.baseURI = "https://reqres.in/api/users";


		// Make a GET request to the endpoint with path parameter id=3
		Response response = RestAssured.given()
				.pathParam("id", 3)
				.when()
				.get("/{id}");


		// Assert the status code is 200
		response.then().statusCode(200);


		// Assert the value of data.last_name is 'Wong'
		response.then().body("data.last_name", equalTo("Wong"));


		// Print the response
		response.prettyPrint();
	}

	@Test
	public void validateAPIEndpoint05() {
		// Set the Base URI for the API
		RestAssured.baseURI = "https://reqres.in/api";


		// Request body
		String requestBody = "{\n" +
				" \"name\": \"Sam\",\n" +
				" \"job\": \"Project Leader\"\n" +
				"}";


		// Make a post request to the endpoint "/users"
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(requestBody)
				.post("/users");


		// Assert the status code
		Assert.assertEquals(response.getStatusCode(), 201);


		// Assert the response body
		Assert.assertEquals(response.jsonPath().getString("name"), "Sam");
		Assert.assertEquals(response.jsonPath().getString("job"), "Project Leader");


		// Print the response
		System.out.println(response.getBody().asString());
	}

	@Test
	public void validateAPIEndpoint06() {
		// Set the Base URI for the API
		RestAssured.baseURI = "https://reqres.in/api";


		// Request body
		String requestBody = "{\n" +
				" \"name\": \"Sam\",\n" +
				" \"job\": \"Project Leader\"\n" +
				"}";


		// Make a post request to the endpoint "/users"
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(requestBody)
				.post("/users");


		// Assert the status code
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 201);


		// Assert the response body
		String name = response.jsonPath().getString("name");
		Assert.assertEquals(name, "Sam");


		String job = response.jsonPath().getString("job");
		Assert.assertTrue(job.contains("Leader"));

		/*System.out.println("name :" + name + " job : " + job);*/
		System.out.println(response.getBody().asString());
	}


	@Test

	public void testCreateUser01() {

		// Define the base URL of the JSONPlaceholder API

		RestAssured.baseURI = "https://reqres.in/api";


		/*oauth("consumerKey", "consumerSecret", "accessToken", "secretToken");*/
		// Define the request payload (missing 'name' field)

		String username = "username";
		String password = "password";


		String requestBody = "{ \"username\": \"john_doe\", \"email\": \"john@example.com\" }";



		// Send a POST request to create a new user

		RestAssured.given()

				.contentType(ContentType.JSON)

				.body(requestBody)

				.when()

				.post("/register")

				.then()

				.statusCode(400) // Expect a 400 Bad Request status code

				.contentType(ContentType.JSON) // Expect JSON response

				.body("error", equalTo("Bad Request")); // Expect the error message

	}


	@Test

	public void testGetUser() {

		// Define the base URL of the JSONPlaceholder API

		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";



		// Send a GET request to retrieve a user by ID (e.g., user with ID 1)

		RestAssured.given()

				.when().log().all()

				.get("/users/1")

				.then()

				.statusCode(200) // Expect a 200 OK status code

				.contentType("application/json; charset=utf-8") // Expect JSON response

				.body("id", equalTo(1)) // Expect the user's ID to be 1

				.body("name", equalTo("Leanne Graham")) // Expect the user's name

				.body("emailId", equalTo("Sincere@april.biz")); // Expect the user's email

	}


    @Test
    public void testDeleteUser() {
        // Set the Base URI for the API
        RestAssured.baseURI = "https://reqres.in/api";

        Response response = RestAssured.given()
                .when()
                .delete("/users/20")
                .then()
                .extract().response();

        Assertions.assertEquals(204, response.statusCode());

}
//check if the file upload is done correctly in /jpa/users/{id}/single-file-upload api
	@Test
	public void testFileUpload() {
		File testUploadFile = new File("C:\\Users\\sandeep_banoth\\Downloads\\Iron Man.jpg"); //Specify your own location and file

        RestAssured.baseURI = "http://localhost:8080";

        Response response = given()

                .multiPart(testUploadFile)

                .when().

                post("jpa/users/2/single-file-upload");



        System.out.println(response.getStatusCode());

        System.out.println(response.asString());

        assertTrue(response.asString().contains("File upload done"));

        assertTrue(response.asString().contains("Iron Man.jpg"));

        /*assertTrue(response.asString().contains("C:\\Users\\sandeep_banoth\\Downloads\\Iron Man.jpg"));*/

       /* assertTrue(response.asString().contains("Iron Man.jpg"));*/

        /*assertTrue(response.asString().contains("C:\\Users\\sandeep_banoth\\Downloads\\Iron Man.jpg"));*/

       /* assertTrue(response.asString().contains("Iron Man.jpg"));*/

       /* assertTrue(response.asString().contains("C:\\Users\\sandeep_banoth\\Downloads\\Iron Man.jpg"));*/
	}


	@Test
	public void testGetUserReposEndpoint() {

		RestAssured.baseURI = "https://api.github.com";
		Response response = RestAssured.get("/user/repos");

		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 401, "Status code is not 401");

		System.out.println("Response: " + response.asString());
		System.out.println("Status code: " + statusCode);
	}




	@Test
	public void testInvalidEndpoint() {
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
		Response response = RestAssured.get("/invalid-endpoint");

		// Asserting the status code
		Assert.assertEquals(response.getStatusCode(), 404.0, "Status code is not 400");

		// Printing the response
		System.out.println("Response: " + response.getBody().asString());
	}

	@Test

	public void testStatusCode() {

// Specify the ReqRes.in API endpoint that returns a 400 status code

		String apiUrl = "https://reqres.in/api/unknown/23"; // Use an invalid endpoint to trigger a 400



		// Send a GET request to the API

		Response response = RestAssured.given()

				.contentType(ContentType.JSON)

				.get(apiUrl);



		// Verify that the response has a 400 status code

		response.then().statusCode(404);

	}
	@Test
	public void validateAPIEndpoint08() {
		// Set the Base URI for the API
		RestAssured.baseURI = "https://reqres.in/api/users";



		// Make a GET request to the endpoint with path parameter id=3
		Response response = RestAssured.given()
				.pathParam("id", 20)
				.when()
				.get("/{id}");



		// Assert the status code is 200
		response.then().statusCode(200);



		// Assert the value of data.last_name is 'Wong'
		response.then().body("data.last_name", equalTo("Wong"));



		// Print the response
		response.prettyPrint();
	}

	@Test

	public void testStatusCodeFor403() {

		// Define the base URL for the Reqres API

		RestAssured.baseURI = "https://reqres.in/api";



		// Send a GET request to a resource that requires authorization (e.g., a non-existent resource)

		Response response = given()

				.when()

				.get("/nonexistent-resource");



		// Check if the status code is 403 Forbidden

		response.then()

				.statusCode(403);

	}

	@Test

	public void testStatusCodeFor404() {

// Define the base URL for the Reqres.in API

		RestAssured.baseURI = "https://reqres.in/api";



		// Send a GET request to a resource that requires authorization (e.g., /api/unknown/23)

		Response response = given()

				.when()

				.get("/unknown/23");



		// Check if the status code is 403 Forbidden

		response.then()

				.statusCode(403);

	}



	@Test
	public void testGetUserReposEndpoint01() {
		RestAssured.baseURI = "https://reqres.in/api";
		Response response = RestAssured.get("/register");



		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 404, "Status code is not 401");



		System.out.println("Response: " + response.asString());
		System.out.println("Status code: " + statusCode);
	}

	/*@Test

	public void testMissingAuthentication() {

// Set the base URI of the ReqRes.in API

		RestAssured.baseURI = "https://reqres.in/api";



		// Send a GET request to an endpoint that requires authentication (e.g., /users)

		Response response = RestAssured.given()

				.when()

				.get("/users");



		// Check the response status code

		int statusCode = response.getStatusCode();



		// Assert that the status code is 401 (Unauthorized) since authentication is missing

		org.testng.Assert.assertEquals(statusCode, 401);



		// You can also check the response body or headers for additional information

		// String responseBody = response.getBody().asString();

		// System.out.println("Response Body: " + responseBody);

	}*/

	/*private static final String BASE_URI = "https://api.github.com";
	private static final String ENDPOINT = "/repos/octocat/private-repo";
	private static final String ACCESS_TOKEN = "your_access_token";*/






	@Test
	public void testAPIEndpoint() {
		 final String BASE_URI = "https://api.github.com";
		 final String ENDPOINT = "/repos/octocat/private-repo";
		 final String ACCESS_TOKEN = "your_access_token";

		RestAssured.baseURI = BASE_URI;

		AuthenticationScheme authenticationScheme = new OAuth2Scheme();
		RestAssured.authentication = authenticationScheme;



		Response response = RestAssured.get(ENDPOINT);



		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 403, "Status code is not 403");



		String responseBody = response.getBody().asString();
		System.out.println("Response Body: " + responseBody);
	}
	@Test

	public void jsonIterateArr() {



		//base URI with Rest Assured class

		RestAssured.baseURI = "https://run.mocky.io/v3";



		//obtain Response from GET request

		Response res = given()

				.when()

				.get("/8ec8f4f7-8e68-4f4b-ad18-4f0940d40bb7");



		//convert JSON to string

		JsonPath j = new JsonPath(res.asString());



		//get values of JSON array after getting array size

		int s = j.getInt("Location.size()");

		for(int i = 0; i < s; i++) {

			String state = j.getString("Location["+i+"].State");

			String zip = j.getString("Location["+i+"].zip");

			System.out.println(state);

			System.out.println(zip);

		}

	}





	@Test
	public void validatePatchRequest() {

		RestAssured.baseURI = "https://reqres.in/api";
		String requestBody = "{\n" + " \"name\": \"Isha\" \n" + "}";



		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(requestBody)
				.patch("/users/2");



		int statusCode = response.getStatusCode();
		String name = response.jsonPath().getString("name");



		Assert.assertEquals(statusCode, 201);
		Assert.assertEquals(name, "Isha");
	}

}





