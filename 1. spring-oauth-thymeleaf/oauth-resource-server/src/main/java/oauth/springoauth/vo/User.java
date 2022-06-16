package oauth.springoauth.vo;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Collection;

@Data
@Entity
public class User implements UserDetails {

    @Id
    @Column(nullable = true, unique = true, length = 20)
    private String id;

    @Column(length = 100)
    private String password;

    @Column(nullable = false, unique = true, length = 100)
    private String nickname;

    @Column(nullable = true, unique = false)
    private String state; // Y : 정상 회원 , L : 잠긴 계정, P : 패스워드 만료, A: 계정 만료

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.id;
    }

    @Override
    public boolean isAccountNonExpired() {
        if(StringUtils.equalsIgnoreCase(state, "A")){
            return false;
        }
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if(StringUtils.equalsIgnoreCase(state, "L")){
            return false;
        }
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        if(StringUtils.equalsIgnoreCase(state, "P")){
            return false;
        }
        return true;
    }

    @Override
    public boolean isEnabled() {
        if( isAccountNonExpired() && isAccountNonExpired() && isCredentialsNonExpired()){
            return true;
        }
        return false;
    }
}
