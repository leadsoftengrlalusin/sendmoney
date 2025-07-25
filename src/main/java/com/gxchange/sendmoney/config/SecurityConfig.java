package com.gxchange.sendmoney.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/h2-console/**",
                                "/api/register",
                                "/error",
                                "/"
                        ).permitAll() // Allow access without authentication
                        .anyRequest().authenticated() // Require auth for everything else
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**", "/api/**") // Avoid CSRF for console + APIs
                )
                .headers(headers -> headers.frameOptions(frame -> frame.disable())) // Allow frame for H2 console
                .httpBasic(withDefaults()) // Enable basic auth for protected endpoints
                .formLogin(form -> form.disable()); // Disable form login (optional)

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
