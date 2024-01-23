package com.gorest.crudsuite;

import com.gorest.gorestinfo.UserSteps;
import com.gorest.testbase.TestBase;
import com.gorest.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserCRUDTestWithSteps extends TestBase {
    static String name = TestUtils.getRandomValue() + "Vijay";
    static String email = TestUtils.getRandomValue() + "vijay123@gmail.com";
    static String gender = "Male";
    static String status = "Active";
    static int userId;
    @Steps
    UserSteps steps;

    @Title("Get all Users Details")
    @Test
    public void test01_getAllUsers() {
        ValidatableResponse response = steps.getAllUsers();
        response.log().all().statusCode(200);
    }

    @Title("This will create a new user")
    @Test
    public void test02_CreateUser() {
        ValidatableResponse response = steps.createUser(name, email, gender, status);
        response.log().all().statusCode(201);
        userId = response.log().all().extract().path("id");
        System.out.println(userId);
    }

    @Title("Verify User Added successfully")
    @Test
    public void test03_VerifyUserId() {
        HashMap<String, Object> userMap = steps.getUserInfoByUserID(userId);

        Assert.assertThat(userMap, hasValue(name));

    }

    @Title("Update the user information and verify the updated information")
    @Test
    public void test04_UpdateUser() {
        name = "Vijay" + TestUtils.getRandomValue();
        email = "vijay123@gmail.com" + TestUtils.getRandomValue();
        steps.updateUser(userId, name, email, gender, status).statusCode(200);

        HashMap<String, Object> userMap = steps.getUserInfoByUserID(userId);
        Assert.assertThat(userMap, hasValue(userId));
    }

    @Title("Delete the user and verify if the user is deleted")
    @Test
    public void test05_DeleteUserAndVerify() {
        steps.deleteUser(userId).statusCode(204);

        steps.getUserById(userId).statusCode(404);
    }
}
