package restassured;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.ContactDTO;
import dto.ContactResponseDTO;
import dto.ErrorDTO;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static helpers.Helper.*;
import static helpers.Helper.TOKEN;

public class EditContact {
    String endpoint = "contacts";
    String id;

    @BeforeMethod
    public void precondition(){
        RestAssured.baseURI = BASE_URI;
        RestAssured.basePath = PATH;
        ContactDTO contactDTO = ContactDTO.builder()
                .name("QA38")
                .lastName("Automation")
                .email("qa38_" + i + "@mail.com")
                .phone("12345678" + i)
                .address("Rehovot")
                .description("Students")
                .build();

        ContactResponseDTO responseDTO = given()
                .header(authHeader, TOKEN)
                .body(contactDTO)
                .contentType(ContentType.JSON)
                .when()
                .post(endpoint)
                .then()
                .assertThat().statusCode(200)
                .extract()
                .as(ContactResponseDTO.class);

        String message = responseDTO.getMessage();
        System.out.println(message);
        id = message.substring(message.lastIndexOf(" ") + 1);

    }

    @Test
    public void editContactTest(){
        ContactDTO contactDTO = ContactDTO.builder()
                .id(id)
                .name("QA38")
                .lastName("Automation")
                .email("qa38_" + i + "@mail.com")
                .phone("12345678" + i)
                .address("Rehovot")
                .description("Teacher")
                .build();

        ContactResponseDTO responseDTO = given()
                .header(authHeader, TOKEN)
                .body(contactDTO)
                .contentType(ContentType.JSON)
                .when()
                .put(endpoint)
                .then()
                .assertThat().statusCode(200)
                .extract()
                .as(ContactResponseDTO.class);
        System.out.println(responseDTO.getMessage());
        System.out.println(contactDTO.getDescription());

    }
    @Test
    public void editContactTestIdNotFound(){
        ContactDTO contactDTO = ContactDTO.builder()
                .id("5561")
                .name("QA38")
                .lastName("Automation")
                .email("qa38_" + i + "@mail.com")
                .phone("12345678" + i)
                .address("Rehovot")
                .description("Teacher")
                .build();

        ErrorDTO responseDTO = given()
                .header(authHeader, TOKEN)
                .body(contactDTO)
                .contentType(ContentType.JSON)
                .when()
                .put(endpoint)
                .then()
                .assertThat().statusCode(404)
                .extract()
                .as(ErrorDTO.class);
        System.out.println(responseDTO.getMessage());


    }
    @Test
    public void editContactTestWrongData(){
        ContactDTO contactDTO = ContactDTO.builder()
                .id(id)
                .name("QA38")
                .lastName("Automation")
                .email("qa38_" + i + "@mail.com")
                .phone("178" + i)
                .address("Rehovot")
                .description("Teacher")
                .build();

        ErrorDTO responseDTO = given()
                .header(authHeader, TOKEN)
                .body(contactDTO)
                .contentType(ContentType.JSON)
                .when()
                .put(endpoint)
                .then()
                .assertThat().statusCode(400)
                .extract()
                .as(ErrorDTO.class);
        System.out.println(responseDTO.getMessage());
    }
    @Test
    public void editContactTestUnauthrized(){
        ContactDTO contactDTO = ContactDTO.builder()
                .id(id)
                .name("QA38")
                .lastName("Automation")
                .email("qa38_" + i + "@mail.com")
                .phone("12345678" + i)
                .address("Rehovot")
                .description("Teacher")
                .build();

        ErrorDTO responseDTO = given()
                .header(authHeader, 453483)
                .body(contactDTO)
                .contentType(ContentType.JSON)
                .when()
                .put(endpoint)
                .then()
                .assertThat().statusCode(401)
                .extract()
                .as(ErrorDTO.class);
        System.out.println(responseDTO.getMessage());


    }
}
