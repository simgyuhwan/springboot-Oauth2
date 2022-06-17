package com.example.demo;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuthorizationCodeListTest {
    public final static String AUTH_SERVER = "http://localhost:8083/auth/realms/baeldung/protocol/openid-connect";
    public final static String RESOURCE_SERVER = "http://localhost:8081/resource-server";
    private final static String REDIRECT_URL = "http://localhost:8082/new-client/login/oauth2/code/custom";
    private final static String CLIENT_ID = "newClient";
    private final static String CLIENT_SECRET = "newClientSecret";

    @Test
    public void givenUser_whenUserFooClient_thenOKForFooResourceOnly(){
        final String accessToken = obtainAccessTokenWithAuthorizationCode("john@test.com", "123");
        final Response fooResponse = RestAssured.given().header("Authorization", "Bearer" + accessToken)
                .get(RESOURCE_SERVER + "/api/foos/1");
        assertEquals(200, fooResponse.getStatusCode());
        assertNotNull(fooResponse.jsonPath().get("name"));
    }


    private String obtainAccessTokenWithAuthorizationCode(String username, String password){
        String authorizeUri = AUTH_SERVER + "/auth";
        String tokenUrl = AUTH_SERVER + "/token";

        // 처음 요청
        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("client_id", CLIENT_ID);
        loginParams.put("response_type", "code");
        loginParams.put("redirect_uri", REDIRECT_URL);
        loginParams.put("scope", "read write");

        // user login
        // 인증 서버 url에 로그인 정보를 전달하고 요청의 응답 값을 받게 된다.
        Response response = RestAssured.given().formParams(loginParams).get(authorizeUri);
        // 응답 값에서 cookie 를 가져옴
        String cookieValue = response.getCookie("AUTH_SESSION_ID");

        // ??
        String authUrlWithCode = response.htmlPath().getString("'**'.find{node -> node.name() = 'form'}*.@action");

        // get code
        Map<String, String> codeParams = new HashMap<>();
        codeParams.put("username", username);
        codeParams.put("password", password);

        // 추출했던 쿠키 값을 넣은 후, 인증 서버에 코드를 넣고 요청
        response = RestAssured.given().cookie("AUTH_SESSION_ID", cookieValue).formParams(codeParams)
                .post(authUrlWithCode);

        final String location = response.getHeader(HttpHeaders.LOCATION);

        // 로그인 정보를 전달해서 받은 Http 상태 값이 FOUND 인지 확인
        assertEquals(HttpStatus.FOUND.value(), response.getStatusCode());
        final String code = location.split("#|=|&")[3];

        // get access token
        Map<String, String> tokenParams = new HashMap<>();
        tokenParams.put("grant_type", "authorization_code");
        tokenParams.put("client_id", CLIENT_ID);
        tokenParams.put("client_secret", CLIENT_SECRET);
        tokenParams.put("redirect_uri", REDIRECT_URL);
        tokenParams.put("code", code);

         response = RestAssured.given().formParams(tokenParams)
                .post(tokenUrl);

         return response.jsonPath().getString("access_token");
    }

}
