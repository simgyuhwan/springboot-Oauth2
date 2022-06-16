package oauth.springoauth.vo;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class AccessToken {
    public String code;
    private String grant_type;
    private String redirect_uri;

    public AccessToken setup(String code, String grant_type, String redirect_uri){
        this.code = code;
        this.grant_type = grant_type;
        this.redirect_uri = redirect_uri;
        return this;
    }

    public Map getMapData() {
        Map map = new HashMap();
        map.put("code", code);
        map.put("grant_type", grant_type);
        map.put("redirect_uri", redirect_uri);
        return map;
    }
}
