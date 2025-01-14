package com.mysql.spring_jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mysql.spring_jwt.filter.JwtAuthenticationFilter;
import com.mysql.spring_jwt.service.auth.UserDetailsServiceImp;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    private final UserDetailsServiceImp userDetailsServiceImp;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(UserDetailsServiceImp userDetailsServiceImp, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(
                req -> req
                    .requestMatchers(
                        "/api/auth/**",
                        "/api/storage/**",
                        "/api/articles",
                        "/api/articles/{id}",
                        "/api/categories/{id}",
                        "/api/categories"
                    )
                    .permitAll()
                    .requestMatchers(
                        "/api/demo/admin/**"
                    )
                    .hasAnyAuthority("ADMIN")
                    .requestMatchers(
                        "/api/account/**",
                        "/api/articles/store",
                        "/api/articles/{id}/**",
                        "/api/categories/store",
                        "/api/categories/{id}/**"
                    )
                    .authenticated()
                    .anyRequest()
                    .denyAll()
            )
            .userDetailsService(userDetailsServiceImp)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // middleware
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
