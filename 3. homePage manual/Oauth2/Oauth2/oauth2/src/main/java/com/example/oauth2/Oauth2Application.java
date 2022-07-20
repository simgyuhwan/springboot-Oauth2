package com.example.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@SpringBootApplication
@RestController
public class Oauth2Application{
	@GetMapping("/user")
	public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal){
		Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();
		System.out.println("사용자 이름: ");
		System.out.print(principal.getName());

		return Collections.singletonMap("name", principal.getAttribute("name"));
	}

	@GetMapping("/hello")
	public String test(){
		return "test";
	}

	public static void main(String[] args) {
		SpringApplication.run(Oauth2Application.class, args);
	}
}
