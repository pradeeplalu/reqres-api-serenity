package in.reqres.Userinfo;

import in.reqres.steps.UserSteps;
import in.reqres.testbase.TestBase;
import in.reqres.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class UsersCRUDTest extends TestBase {
    static String name = "Bob" + TestUtils.getRandomValue();
    static String job = "API Tester" + TestUtils.getRandomValue();
    static String email = "eve.holt@reqres.in";
    static String password = "cityslicka";
    static String userID;
    static String token;


    @Title("This will create a user")
    @Test
    public void test001() {
        UserSteps userSteps;

        ValidatableResponse response = UserSteps.createUser(name, job);
        response.log().all().statusCode(201);
        userID = response.log().all().extract().path("id");
        System.out.println(userID);
    }

    @Title("Verify is User is added or not")
    @Test
    public void test002() {
        HashMap<?, ?> userMap = UserSteps.getUserInfoById(userID);
        Assert.assertThat(userMap, hasValue(userID));
        System.out.println(userID);
    }

    @Title("This will login a user")
    @Test
    public void test003() {
        HashMap<String, ?> tokenMap = UserSteps.loginUser(email, password);
        Assert.assertThat(tokenMap, hasKey("token"));
        System.out.println(tokenMap);
    }

    @Title("This will update a user by PUT")
    @Test
    public void test004() {
        name = name + "_updatedbyPut";
        ValidatableResponse response = UserSteps.updateUserByPut(userID, name, job);
        response.log().all().statusCode(200);

    }

    @Title("This will update a user by Patch")
    @Test
    public void test005() {
        name = name + "_updatedbyPatch";
        ValidatableResponse response = UserSteps.updateUserByPatch(userID, name, job);
        response.log().all().statusCode(200);
    }

    @Title("This will update a user by Patch")
    @Test
    public void test006() {
        ValidatableResponse response = UserSteps.deleteProduct(userID);
        response.log().all().statusCode(204);
    }

    @Title("1. This will verify if Page = 2")
    @Test
    public void test007() {
        ValidatableResponse response = UserSteps.getAllUserFromPageTwo();
        int page = response.log().all().extract().path("page");
        Assert.assertEquals(2, page);
    }

    @Title("2. This will if verify Per_page = 6")
    @Test
    public void test008() {
        ValidatableResponse response = UserSteps.getAllUserFromPageTwo();
        int per_page = response.log().all().extract().path("per_page");
        Assert.assertEquals(6, per_page);
    }

    @Title("3. This will if verify second data id = 8")
    @Test
    public void test009() {
        ValidatableResponse response = UserSteps.getAllUserFromPageTwo();
        int data = response.log().all().extract().path("data[1].id");
        Assert.assertEquals(8, data);
    }

    @Title("4. This will if verify forth data first_name = Byron")
    @Test
    public void test010() {
        ValidatableResponse response = UserSteps.getAllUserFromPageTwo();
        String firstname = response.log().all().extract().path("data[3].first_name");
        Assert.assertEquals("Byron", firstname);
    }

    @Title("5. This will if verify list of data = 6")
    @Test
    public void test011() {
        ValidatableResponse response = UserSteps.getAllUserFromPageTwo();
        List<?> listOfData= response.log().all().extract().path("data");
        Assert.assertEquals(6, listOfData.size());
    }

    @Title("6. This will if verify fifth data avatar=https://reqres.in/img/faces/12-image.jpg")
    @Test
    public void test012() {
        ValidatableResponse response = UserSteps.getAllUserFromPageTwo();
        String imageUrl = response.log().all().extract().path("data[5].avatar");
        Assert.assertEquals("https://reqres.in/img/faces/12-image.jpg", imageUrl);
    }

    @Title("7. This will if verify support.url =https://reqres.in/#support-heading")
    @Test
    public void test013() {
        ValidatableResponse response = UserSteps.getAllUserFromPageTwo();
        String supportHeading = response.log().all().extract().path("support.url");
        Assert.assertEquals("https://reqres.in/#support-heading", supportHeading);
    }

    @Title("8. This will if verify support.text = To keep ReqRes free, contributions towards server costs are appreciated!")
    @Test
    public void test014() {
        ValidatableResponse response = UserSteps.getAllUserFromPageTwo();
        String supportText = response.log().all().extract().path("support.text");
        Assert.assertEquals("To keep ReqRes free, contributions towards server costs are appreciated!", supportText);
    }

}
