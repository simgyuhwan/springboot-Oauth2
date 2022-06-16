package com.example.demo;

public class AuthorizationCodeListTest {
    public final static String AUTH_SERVER = "http://localhost:8083/auth/realms/baeldung/protocol/openid-connect";
    public final static String RESOURCE_SERVER = "http://localhost:8081/resource-server";
    private final static String REDIRECT_URL = "http://localhost:8082/new-client/login/oauth2/code/custom";
    private final static String CLIENT_ID = "newClient";
    private final static String CLIENT_SECRET = "newClientSecret";


    private String obtainAccessTokenWithAuthorizationCode(String username, String password){

    }

}
