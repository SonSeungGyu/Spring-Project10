package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();//단방향 엔진
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//메뉴별 접근제한 설정
		http.authorizeHttpRequests()
				.requestMatchers("/register").permitAll()//회원가입은 아무나 접근 가능
				.requestMatchers("/assets/*","/css/*","/js/*").permitAll()//리소스는 아무나 접근 가능
				.requestMatchers("/").authenticated()//메인화면은 로그인한 사용자이면 접근 가능
				.requestMatchers("/board/*").hasAnyRole("ADMIN","USER")//게시물 관리는 관리자 또는 사용자면 접근 가능
				.requestMatchers("/comment/*").hasAnyRole("ADMIN","USER")
				.requestMatchers("/member/*").hasRole("ADMIN");//회원 관리는 관리자만 접근 가능
															//ROLE_는 이 config 에서만 뺴주고 나머지는 넣어줌
		http.formLogin();
		http.logout();
		
		//csrf 설정 해제
		http.csrf().disable();
		
		return http.build();
	}
}
