import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class APITest {

    private static String id;
    private static String testName;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    public void testGetUser() {
        given()
                .when()
                .get("/users/2")
                .then()
                .statusCode(200)
                .body("data.first_name", equalTo("Janet"));
    }

    @Test(dependsOnMethods = "testGetUser")
    public void testGetUserById() {
        given()
                .pathParam("id", 3)
                .when()
                .get("/users/{id}")
                .then()
                .statusCode(300)
                .body("data.last_name", equalTo("Wong"));
    }

    @Test(dependsOnMethods = "testGetUserById")
    public void testGetUsersWithQueryParams() {
        given()
                .queryParam("page", 2)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("data[1].first_name", equalTo("Lindsay"));
    }

    @Test(dependsOnMethods = "testGetUsersWithQueryParams")
    public void testCreateUser() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{\n" + " \"name\": \"Sam\", \n" + " \"job\": \"Project Leader\"\n" + "}")
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("Sam"))
                .body("job", containsString("Leader"))
                .extract().response();

        id = response.jsonPath().getString("id");
        testName = "testCreateUser";
    }

    @Test(dependsOnMethods = "testCreateUser")
    public void testUpdateUser() {
        given()
                .contentType(ContentType.JSON)
                .body("{\n" + " \"name\": \"Adam\", \n" + " \"job\": \"Software Engineer\"\n" + "}")
                .when()
                .put("/users/" + id)
                .then()
                .statusCode(200)
                .body("job", containsString("Software"));
    }

    @Test(dependsOnMethods = "testCreateUser")
    public void testPatchUser() {
        given()
                .contentType(ContentType.JSON)
                .body("{\n" + " \"name\": \"Isha\" \n" + "}")
                .when()
                .patch("/users/" + id)
                .then()
                .statusCode(200)
                .body("name", equalTo("Isha01"));
    }

    @Test(dependsOnMethods = "testCreateUser")
    public void testDeleteUser() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/users/" + id)
                .then()
                .statusCode(204);
    }

    @Test(dependsOnMethods = "testDeleteUser")
    public void testRegisterUser() {
        given()
                .auth()
                .basic("email", "sydney@fife")
                .when()
                .post("/register")
                .then()
                .statusCode(400);
    }

    @Test
    public void testGetRepos() {
        given()
                .baseUri("https://api.github.com")
                .when()
                .get("/user/repos")
                .then()
                .statusCode(401);
    }

    @Test
    public void testGetUserByIdNotFound() {
        given()
                .pathParam("id", 20)
                .when()
                .get("/users/{id}")
                .then()
                .statusCode(404);
    }

    @Test(dependsOnMethods = "testCreateUser")
    public void testUpdateUserById() {
        given()
                .contentType(ContentType.JSON)
                .body("{\n" + " \"name\": \"Prajakta\", \n" + " \"job\": \"Project Manager\"\n" + "}")
                .when()
                .put("/users/" + id)
                .then()
                .statusCode(200)
                .body("name", equalTo("Prajakta"));
    }
}