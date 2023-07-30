package restassured;

import com.jayway.restassured.RestAssured;
import dto.ContactDTO;
import dto.ContactListDTO;
import dto.ErrorDTO;
import helpers.Helper;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;

public class GetAllContactsTests implements Helper {

    String endpoint = "contacts";

    @BeforeMethod
    public void precondition(){
        RestAssured.baseURI = BASE_URI;
        RestAssured.basePath = PATH;
    }

    @Test
    public void getAllContactsPositive(){

        ContactListDTO listDTO = given()
                .header(authHeader, TOKEN)
                .when()
                .get(endpoint)
                .then()
                .assertThat().statusCode(200)
                .extract()
                .as(ContactListDTO.class);

        for(ContactDTO contactDTO : listDTO.getContacts()){
            System.out.println(contactDTO.getId());
            System.out.println(contactDTO.getEmail());
            System.out.println("===================================");
        }

    }
    @Test
    public void getAllContactsNegative(){

        ErrorDTO response = given()
                .header(authHeader, "sdgrsdrhsdh")
                .when()
                .get(endpoint)
                .then()
                .assertThat().statusCode(401)
                .extract()
                .as(ErrorDTO.class);
        System.out.println(response.getMessage());
    }
}
