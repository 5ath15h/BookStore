package stepdefinition;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.*;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Getbooks {

	private static String BASE_URL = null;
	RequestSpecification requestSpecification;
	ResponseSpecification responseSpecification;
	File payload = new File("../BookStore/src/test/resources/Input/createuser.json");
	private static Response response;
	private String token;
	private String UUID;

	@Before
	public void initialize() throws IOException {
		FileInputStream reader = new FileInputStream("../BookStore/src/test/resources/Input/config.properties");
		Properties prop = new Properties();
		prop.load(reader);
		BASE_URL = prop.getProperty("baseurl");
	}

	@Given("User navigate to bookstore API")
	public void user_navigate_to_bookstore_API() {
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
		requestSpecBuilder.setBaseUri(BASE_URL).setContentType(ContentType.JSON);
		requestSpecification = requestSpecBuilder.build();
		ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
		responseSpecBuilder.expectStatusCode(200).expectContentType(ContentType.JSON);
		responseSpecification = responseSpecBuilder.build();
	}

	@When("post api request with username and password details")
	public void post_api_request_with_username_and_password_details() {
		response = given().spec(requestSpecification).body(payload).when().post("/Account/v1/User").then().log().all()
				.extract().response();
		UUID = response.jsonPath().get("userID");
	}

	@When("send request to generate autorized token")
	public void send_request_to_generate_autorized_token() {
		// Write code here that turns the phrase above into concrete actions
		response = given().spec(requestSpecification).body(payload).when().post("/Account/v1/GenerateToken").then()
				.log().all().extract().response();
		token = response.jsonPath().get("token");
		System.out.println("base token" + token);
	}

	@When("send authorized token details to api request")
	public void send_authorized_token_details_to_api_request() {
		response = given().spec(requestSpecification).header("Authorization", "Bearer " + token).when()
				.get("/Account/v1/User/" + UUID).then().log().all().extract().response();
		System.out.println("user details retrived.." + response.asString());

	}

	@Then("delete the created user")
	public void delete_the_created_user() {
		response = given().spec(requestSpecification).header("Authorization", "Bearer " + token).when()
				.delete("/Account/v1/User/" + UUID).then().log().all().extract().response();
		System.out.println("user deleted" + response.asString());

	}

}
