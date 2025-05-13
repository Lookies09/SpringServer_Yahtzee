package com.example.unityconnect.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // CSRF 비활성화 (API 사용 시 필수)
            .authorizeHttpRequests()
                .requestMatchers("/unity/signup", "/unity/signin", "/unity/setscore").permitAll()                .anyRequest().authenticated() // 그 외는 인증 필요
            .and()
            .httpBasic(); // 기본 인증 방식 사용 (원하는 방식으로 바꿀 수 있음)

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }
}