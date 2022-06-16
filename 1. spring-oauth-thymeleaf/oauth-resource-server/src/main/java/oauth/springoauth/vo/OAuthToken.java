package oauth.springoauth.vo;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.Map;

@Data
public class OAuthToken {

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class response{
        private String access_token;
        private String token_type;
        private String refresh_token;
        private long expires_in;
        private String scope;
    }

    @Data
    public static class request {


        @Data
        public static class refreshToken {
            private String refreshToken;
            private String grant_type;

            public Map getMapData() {
                Map map = new HashMap();
                map.put("refresh_token", refreshToken);
                map.put("grant_type", grant_type);
                return map;
            }
        }
    }

}
