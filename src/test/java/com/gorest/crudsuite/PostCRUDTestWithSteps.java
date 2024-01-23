package com.gorest.crudsuite;

import com.gorest.gorestinfo.PostSteps;
import com.gorest.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PostCRUDTestWithSteps {
    String id = TestUtils.getRandomValue();
    String user_id = TestUtils.getRandomValue();
    String title = "Vijay" + TestUtils.getRandomValue();
    String body = "abc";
    static int postId;

    @Steps
    PostSteps steps;

    @Title("Get all Posts Details")
    @Test
    public void test01_getAllUsers() {
        ValidatableResponse response = steps.getAllPosts();
        response.statusCode(200);
    }

    @Title("This will create a new post")
    @Test
    public void test001_CreatePost() {
        ValidatableResponse response = steps.createPost(id, user_id, title, body);
        response.log().all().statusCode(201);
        postId = response.log().all().extract().path("id");
        System.out.println(postId);

    }

    @Title("Update post information")
    @Test
    public void test003_UpdatePost() {
        title = "Vijay" + " Updated";
        ValidatableResponse response = steps.updatePost(id, user_id, title, body).statusCode(200);
    }

    @Title("This will get info by ID")
    @Test
    public void test004_GetPost() {
        ValidatableResponse response = steps.getPostId(id);
        response.log().all();
    }

    @Title("This will Delete the post information")
    @Test
    public void test005_DeletePost() {
        steps.deletePostId(id).statusCode(204);

        steps.getPostId(id).statusCode(404);
    }
}
