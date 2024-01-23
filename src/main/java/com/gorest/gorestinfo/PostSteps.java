package com.gorest.gorestinfo;

import com.gorest.constants.EndPoints;
import com.gorest.constants.Path;
import com.gorest.model.PostsPojo;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;

public class PostSteps {
    @Step("Get all post information")
    public ValidatableResponse getAllPosts() {
        return SerenityRest.given().log().all()
                .when()
                .get(Path.POSTS)
                .then()
                .statusCode(200);

    }

    @Step("Creating post with id : {0}, user_id : {1}, title : {2}, body : {3}")
    public ValidatableResponse createPost(String id, String user_id, String title, String body) {

        PostsPojo postsPojo = new PostsPojo();
        postsPojo.setId(id);
        postsPojo.setUser_id(user_id);
        postsPojo.setTitle(title);
        postsPojo.setBody(body);

        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer 10efcc0eda0dc46c3d8b3d415af61bad8412b32398edede555b02a9ab8860eb4")
                .when()
                .body(postsPojo)
                .post(Path.POSTS)
                .then();
    }

    @Step("Update post with id : {0},user_id : {1}, title : {2}, body : {3}")
    public ValidatableResponse updatePost(String id, String user_id, String title, String body) {

        PostsPojo postsPojo = new PostsPojo();
        postsPojo.setId(id);
        postsPojo.setUser_id(user_id);
        postsPojo.setTitle(title);
        postsPojo.setBody(body);
        return SerenityRest.given()
                .header("Authorization", "Bearer 10efcc0eda0dc46c3d8b3d415af61bad8412b32398edede555b02a9ab8860eb4")
                .header("Content-Type", "application/json")
                .pathParam("postID", id)
                .when()
                .body(postsPojo)
                .put(Path.POSTS + EndPoints.UPDATE_POST_BY_ID)
                .then().log().all();
    }

    @Step("Delete post information with id : {0}")
    public ValidatableResponse deletePostId(String id) {
        return SerenityRest.given()
                .header("Authorization", "Bearer 10efcc0eda0dc46c3d8b3d415af61bad8412b32398edede555b02a9ab8860eb4")
                .header("Content-Type", "application/json")
                .pathParam("postID", id)
                .when()
                .delete(Path.POSTS + EndPoints.DELETE_POST_BY_ID)
                .then().log().all();
    }

    @Step("Getting store information with storeid : {0}")
    public ValidatableResponse getPostId(String id) {
        return SerenityRest.given()
                .pathParam("postID", id)
                .when()
                .get(Path.POSTS + EndPoints.GET_SINGLE_POST_BY_ID)
                .then().log().all();
    }
}
