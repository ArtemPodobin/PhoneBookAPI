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

public class DeleteContact {
    String id;
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
        id = message.substring(message.lastIndexOf(" ") + 1);


    }
    @Test
    public void deliteContactById(){
        ContactResponseDTO responseDTO = given()
                .header(authHeader, TOKEN)
                .body(id)
                .contentType(ContentType.JSON)
                .when()
                .delete(endpoint + "/" + id)
                .then()
                .assertThat().statusCode(200)
                .extract()
                .as(ContactResponseDTO.class);

        System.out.println(responseDTO.getMessage());
    }
    @Test
    public void deliteContactByIdNotFound(){
        id ="f15c99c2-0b14-4263-b1a0-7b67bd4h36f8";
        ErrorDTO errorResponse = given()
                .header(authHeader, TOKEN)
                .body(id)
                .contentType(ContentType.JSON)
                .when()
                .delete(endpoint + "/" + id)
                .then()
                .assertThat().statusCode(404)
                .extract()
                .as(ErrorDTO.class);

        System.out.println(errorResponse.getMessage());
    }
    @Test
    public void deliteContactByIdUnauthorized(){
        ErrorDTO errorResponse = given()
                .header(authHeader, "fdbzdfbfgn54zf")
                .body(id)
                .contentType(ContentType.JSON)
                .when()
                .delete(endpoint + "/" + id)
                .then()
                .assertThat().statusCode(401)
                .extract()
                .as(ErrorDTO.class);

        System.out.println(errorResponse.getMessage());
    }
    @Test
    public void deliteContactByIdWrongData(){
        id ="a";
        ErrorDTO errorResponse = given()
                .header(authHeader, TOKEN)
                .body(id)
                .contentType(ContentType.JSON)
                .when()
                .delete(endpoint + "/" + id)
                .then()
                .assertThat().statusCode(400)
                .extract()
                .as(ErrorDTO.class);

        System.out.println(errorResponse.getMessage());
    }


}
