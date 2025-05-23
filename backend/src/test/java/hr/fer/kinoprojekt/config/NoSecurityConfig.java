
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
                .securityMatchers(matchers -> matchers.anyRequest()) 
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) 
                .csrf(csrf -> csrf.disable());
        return http.build();
    }
}
