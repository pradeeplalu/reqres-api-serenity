package in.reqres.steps;

import in.reqres.constants.EndPoints;
import in.reqres.model.UserPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class UserSteps {
    @Step("Creating User with name : {0}, Job: {1}")
    public static ValidatableResponse createUser(String name,
                                                 String job) {
        UserPojo reqresPojo = new UserPojo();
        reqresPojo.setName(name);
        reqresPojo.setJob(job);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(reqresPojo)
                .when()
                .post(EndPoints.GET_ALL_USERS)
                .then();
    }

    @Step("Getting All Users from Page 2")
    public static ValidatableResponse getAllUserFromPageTwo() {
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .queryParam("page",2)
                .when()
                .get(EndPoints.GET_ALL_USERS)
                .then();
    }

    @Step("Getting the User information with ID: {0}")
    public static HashMap<?, ?> getUserInfoById(String userID) {
        HashMap<?, ?> userMap = SerenityRest.given().log().all()
                .when()
                .pathParam("userID", userID)
                .get(EndPoints.GET_USER_BY_ID)
                .then()
                .statusCode(200)
                .extract().path("");

        return userMap;
    }

    @Step("Login User with email : {0}, password: {1}")
    public static HashMap<String, ?> loginUser(String email,
                                               String password) {
        UserPojo userPojo = new UserPojo();
        userPojo.setEmail(email);
        userPojo.setPassword(password);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(userPojo)
                .when()
                .post(EndPoints.USER_LOGIN)
                .then()
                .statusCode(200)
                .extract()
                .path("");
    }

    @Step("Update User with name : {0}, Job: {1}")
    public static ValidatableResponse updateUserByPatch(String userID,
                                                        String name,
                                                        String job) {
        UserPojo reqresPojo = new UserPojo();
        reqresPojo.setName(name);
        reqresPojo.setJob(job);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("userID", userID)
                .body(reqresPojo)
                .when()
                .patch(EndPoints.UPDATE_USER_BY_ID)
                .then();
    }

    @Step("Update User with name : {0}, Job: {1}")
    public static ValidatableResponse updateUserByPut(String userID,
                                                      String name,
                                                      String job) {
        UserPojo reqresPojo = new UserPojo();
        reqresPojo.setName(name);
        reqresPojo.setJob(job);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("userID", userID)
                .body(reqresPojo)
                .when()
                .put(EndPoints.UPDATE_USER_BY_ID)
                .then();
    }

    @Step("Deleting user with ID {0}")
    public static ValidatableResponse deleteProduct(String userID) {
        return SerenityRest.given().log().all()
                .pathParam("userID", userID)
                .when()
                .delete(EndPoints.DELETE_USER_BY_ID)
                .then();
    }
}

