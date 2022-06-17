package com.example.demo;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImplicitFlowLiveTest {
    private final static String AUTH_SERVER = "http://localhost:8083/auth/realms/baeldung/protocol/openid-connect";
    private final static String RESOURCE_SERVER = "http://localhost:8081/resource-server";
    private final static String REDIRECT_URL = "http://localhost:8082/new-client/login/oauth2/code/custom";
    private final static String CLIENT_ID = "newClient";
    private final static String USERNAME = "john@test.com";
    private final static String PASSWORD = "123";


    private String obtainAccessToken(String clientId, String username, String password){
        String authorizeUrl = AUTH_SERVER + "/auth";

        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("grant_type", "implicit");
        loginParams.put("client_id", clientId);
        loginParams.put("response_type", "token");
        loginParams.put("redirect_uri", REDIRECT_URL);
        loginParams.put("scope", "read write");

        //user login
        Response response = RestAssured.given().formParams(loginParams).get(authorizeUrl);
        String cookieValue = response.getCookie("AUTH_SESSION_ID");

        String authUrlWithCode = response.htmlPath().getString("'**'.find{node -> node.name()=='form'}*.@action");

        // get access token
        Map<String, String> tokenParams = new HashMap<>();
        tokenParams.put("username", username);
        tokenParams.put("password", password);
        tokenParams.put("client_id",clientId);
        tokenParams.put("redirect_uri", REDIRECT_URL);
        response = RestAssured.given().cookie("AUTH_SESSION_ID", cookieValue).formParams(tokenParams)
                .post(authorizeUrl);

        final String location = response.getHeader(HttpHeaders.LOCATION);

        assertEquals(HttpStatus.FOUND.value(), response.getStatusCode());
        final String accessToken = location.split("#|=|&")[4];
        return accessToken;
    }

}
