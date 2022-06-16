package oauth.springoauth.controller;

import com.sun.jndi.toolkit.url.Uri;
import kong.unirest.Unirest;
import oauth.springoauth.vo.AccessToken;
import oauth.springoauth.vo.OAuthToken;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@RestController
public class OAuthController {

    @RequestMapping("/callback")
    public OAuthToken.response code(@RequestParam String code){

        String cridentials = "clientId:secretKey";

        String encodingCredentials = new String(
                Base64.encodeBase64(cridentials.getBytes(StandardCharsets.UTF_8))
        );

        String requestCode = code;
        AccessToken accessToken = new AccessToken();
        accessToken.setup(requestCode, "authorization_code", "http://localhost:8080/callback");

        OAuthToken.response authorization = Unirest.post("http://localhost:8080/oauth/token"
                ).header("Authorization", "Basic " + encodingCredentials)
                .fields(accessToken.getMapData())
                .asObject(OAuthToken.response.class).getBody();

        return authorization;
    }

}
