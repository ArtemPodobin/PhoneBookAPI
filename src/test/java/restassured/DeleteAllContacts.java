package restassured;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.ContactDTO;
import dto.ContactResponseDTO;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static helpers.Helper.*;
import static helpers.Helper.TOKEN;

public class DeleteAllContacts {

    String endpoint = "contacts";

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
    }

    @Test
    public void DeleteAllContactsPositive(){

        ContactResponseDTO responseDTO = given()
                .header(authHeader, TOKEN)
                .when()
                .delete(endpoint +"/clear")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .as(ContactResponseDTO.class);

        System.out.println(responseDTO.getMessage());
    }

    @Test
    public void DeleteAllContactsUnauthorized(){

        ContactResponseDTO responseDTO = given()
                .header(authHeader, "shalom")
                .when()
                .delete(endpoint +"/clear")
                .then()
                .assertThat().statusCode(401)
                .extract()
                .as(ContactResponseDTO.class);

        System.out.println(responseDTO.getMessage());
    }
}
