package org.ideacloud.security;

import lombok.RequiredArgsConstructor;
import org.ideacloud.filter.RequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {
    private final AccessTokenAuthenticationFilter authenticationFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(
                        "/favicon.ico",
                        "/error",
                        "/",
                        "/index",
                        "/mypage",
                        "/signin",
                        "/signup",
                        "/static/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.NEVER))
            .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()) // OPTIONS 요청 허용
            .authorizeHttpRequests(auth -> auth.requestMatchers("/api/session").permitAll())
            .authorizeHttpRequests(auth -> auth.requestMatchers("/api/admin/session").permitAll())
            .authorizeHttpRequests(auth -> auth.requestMatchers("/api/users/**").permitAll())
            .authorizeHttpRequests(auth -> auth.anyRequest().hasAnyRole("USER", "ADMIN"))
            .exceptionHandling(e -> e.authenticationEntryPoint(authenticationEntryPoint()))
            .exceptionHandling(e -> e.accessDeniedHandler(accessDeniedHandler))
            .addFilterBefore(authenticationFilter, BasicAuthenticationFilter.class)
            .addFilterAfter(new RequestFilter(), BasicAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }
}
