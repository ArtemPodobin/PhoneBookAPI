package restassured;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

import dto.ContactDTO;
import dto.ContactResponseDTO;
import dto.ErrorDTO;
import helpers.Helper;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;



import static com.jayway.restassured.RestAssured.*;

public class AddNewContact implements Helper {

    String endpoint = "contacts";

    @BeforeMethod
    public void precondition() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.basePath = PATH;
    }

    @Test
    public void testAddNewContact(){
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

        System.out.println(responseDTO.getMessage());
    }
    @Test
    public void testAddNewContactDublicate(){
        ContactDTO contactDTO = ContactDTO.builder()
                .name("QA38")
                .lastName("Automation")
                .email("qa38@mail.com")
                .phone("1234567833")
                .address("Rehovot")
                .description("Students")
                .build();

        ErrorDTO ErrorDTO = given()
                .header(authHeader, TOKEN)
                .body(contactDTO)
                .contentType(ContentType.JSON)
                .when()
                .post(endpoint)
                .then()
                .assertThat().statusCode(409)
                .extract()
                .as(dto.ErrorDTO.class);

        System.out.println(ErrorDTO.getMessage());
    }

    @Test
    public void testAddNewContactUnauthorized(){
        ContactDTO contactDTO = ContactDTO.builder()
                .name("QA38")
                .lastName("Automation")
                .email("qa38@mail.com")
                .phone("1234567833")
                .address("Rehovot")
                .description("Students")
                .build();

        ErrorDTO ErrorDTO = given()
                .header(authHeader, "sdthsfdtnhsf45gtn4srf")
                .body(contactDTO)
                .contentType(ContentType.JSON)
                .when()
                .post(endpoint)
                .then()
                .assertThat().statusCode(401)
                .extract()
                .as(dto.ErrorDTO.class);

        System.out.println(ErrorDTO.getMessage());
    }
    @Test
    public void testAddNewContactWrongPhone(){
        ContactDTO contactDTO = ContactDTO.builder()
                .name("QA38")
                .lastName("Automation")
                .email("qa38@mail.com")
                .phone("ghmdg")
                .address("Rehovot")
                .description("Students")
                .build();

        ErrorDTO ErrorDTO = given()
                .header(authHeader, TOKEN)
                .body(contactDTO)
                .contentType(ContentType.JSON)
                .when()
                .post(endpoint)
                .then()
                .assertThat().statusCode(400)
                .extract()
                .as(dto.ErrorDTO.class);

        System.out.println(ErrorDTO.getMessage());
    }

    }

