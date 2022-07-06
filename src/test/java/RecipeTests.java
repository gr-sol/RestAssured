
import io.restassured.RestAssured;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


public class RecipeTests {
    ResponseSpecification responseSpecification = null;

    @BeforeEach
    void BeforeTest() {
        RestAssured.baseURI = "https://reqres.in/";
        responseSpecification = new ResponseSpecBuilder()   // билдер
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(5000L))
                .expectHeader("Server", "cloudflare")
                .build();

    }

    @Test
        // проверки через есерты
    void Test1() {
        JsonPath response = given()
                .when()
                .log().all()
                .get("api/users?page=2")
                .prettyPeek()
                .body()
                .jsonPath();
        assertThat(response.get("data.email[0]"), equalTo("michael.lawson@reqres.in"));

    }

    @Test
        //  обычная схема
    void Test2() {
        given()
                .log().all()
                .when()
                .get("api/users?page=2")
                .prettyPeek()
                .then()
                .spec(responseSpecification)//билдер
                .body("data.email[0]", equalTo("michael.lawson@reqres.in"));
    }

    @Test
        // проверки в given
    void Test3() {
        given()
                .log().all()
                .expect()
                .spec(responseSpecification) //билдер
                .body("data.email[0]", equalTo("michael.lawson@reqres.in"))
                .when()
                .get("api/users?page=2")
                .prettyPeek();
    }

    @Test
        // Извлечение информации из respons, например хедеры
    void Test4() {
        System.out.println("Можно извлечь заголовки" + given().log().all().when().get("api/users?page=2").prettyPeek()
                .then().extract().headers());
    }


}