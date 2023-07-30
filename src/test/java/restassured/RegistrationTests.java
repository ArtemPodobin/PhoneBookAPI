package restassured;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.AuthRequestDTO;
import dto.AuthResponseDTO;
import dto.ErrorDTO;
import helpers.Helper;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;

public class RegistrationTests implements Helper {
    String endpoint ="user/registration/usernamepassword";
    @BeforeMethod
    public void precondition() {


        RestAssured.baseURI = BASE_URI;
        RestAssured.basePath = PATH;
    }
    @Test
    public void registrationTestPositive(){
        AuthRequestDTO request = AuthRequestDTO.builder()
                .username("bonjovie3@mail.com")
                .password("$Qwerty1234")
                .build();
        AuthResponseDTO response = given()
                .body(request)
                .contentType(ContentType.JSON)
                .when()
                .post(endpoint)
                .then()
                .assertThat().statusCode(200)
                .extract()
                .as(AuthResponseDTO.class);
        System.out.println(response.getToken());
    }
    @Test
    public void registrationTestExistingUser(){
        AuthRequestDTO request = AuthRequestDTO.builder()
                .username("bonjovie@mail.com")
                .password("$Qwerty1234")
                .build();
        ErrorDTO response = given()
                .body(request)
                .contentType(ContentType.JSON)
                .when()
                .post(endpoint)
                .then()
                .assertThat().statusCode(409)
                .extract()
                .as(ErrorDTO.class);
        System.out.println(response.getMessage());
    }
    @Test
    public void registrationTestWrongEmail(){
        AuthRequestDTO request = AuthRequestDTO.builder()
                .username("bonjoviemail.com")
                .password("$Qwerty1234")
                .build();
        ErrorDTO response = given()
                .body(request)
                .contentType(ContentType.JSON)
                .when()
                .post(endpoint)
                .then()
                .assertThat().statusCode(400)
                .extract()
                .as(ErrorDTO.class);
        System.out.println(response.getMessage());
    }


}
