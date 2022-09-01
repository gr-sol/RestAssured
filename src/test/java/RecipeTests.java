
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import groovy.json.JsonOutput;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLOutput;
import java.util.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


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
        System.out.println("Можно извлечь заголовки" +
                given()
                        .log()
                        .all()
                        .when()
                        .get("api/users?page=2")
                        .prettyPeek()
                        .then()
                        .extract()
                        .headers());
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    @Test
        //Десерилизация json. Раабота с полями как с объектами
    void Test5() throws JsonProcessingException {
        String body = given()
                .when()
                .get("api/users?page=2")
                .then()
                .extract()
                .body().asString();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        Dat data = objectMapper.readValue(body, Dat.class);
        System.out.println("Ответ  = " + data.data[2].email);
        System.out.println("Ответ  = " + data.data[2].firstName);
        System.out.println("Ответ  = " + data.support.text);


    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Test
        // Добавил асерты
    void Test6() throws JsonProcessingException {
        String firstname = "Tobias";
        String[] array = new String[]{"ab"};
        List<Integer> list = Arrays.asList(5, 2, 4);
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(1, "a");
        map.put(2, "b");
        map.put(3, "c");
        String body = given()
                .when()
                .get("api/users?page=2")
                .then()
                .extract()
                .body().asString();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        Dat data = objectMapper.readValue(body, Dat.class);
        System.out.println("Ответ  = " + data.data[2].email);
        System.out.println("Ответ  = " + data.data[2].firstName);
        System.out.println("Ответ  = " + data.support.text);
        System.out.println("Ответ  = " + data.total);
        assertThat(firstname, equalTo(data.data[2].firstName)); // сравнение значения стринг
        assertThat(data.data[2].firstName, anyOf(containsString("Tob"), endsWith("ias"))); // либо содержит Tob либо кончается на ias
        //assertThat(data.data[2].firstName, not(containsString("Tob"))); // не содержит
        assertThat(data.data[2].firstName, allOf(containsString("Tob"), endsWith("ias"))); // и содержит и кончается
        assertThat(data.total, allOf(greaterThan(5), lessThanOrEqualTo(15), not(equalTo(6))));// больше 5, меньше 15, не равна 6
        assertThat(list, is(not(empty()))); // проверить, что список не пустой
        assertThat(list, hasSize(3)); // проверить размер списка
        assertThat(list, everyItem(greaterThan(0))); // проверить, что все элементы больше 0
        assertThat(list, hasItem(5)); // проверить, что в списке есть 5
        assertThat(list, contains(5, 2, 4)); // проверить, что состоит из элементов
        assertThat(map, hasKey(1)); // содержит ключь
        assertThat(map, hasValue("a")); // содержит значение
        assertThat(map, hasEntry(3, "c")); // содержит запись
        assertThat(array, not(emptyArray()));
        assertThat(array, arrayWithSize(3));

    }


}