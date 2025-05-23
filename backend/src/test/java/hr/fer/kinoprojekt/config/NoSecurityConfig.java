
package hr.fer.kinoprojekt.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@TestConfiguration
public class NoSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatchers(matchers -> matchers.anyRequest()) // Match all requests
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // Allow all
                .csrf(csrf -> csrf.disable()); // Disable CSRF
        return http.build();
    }
}
