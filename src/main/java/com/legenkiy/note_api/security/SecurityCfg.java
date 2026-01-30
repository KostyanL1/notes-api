package com.legenkiy.note_api.security;


import com.legenkiy.note_api.security.filter.JwtAuthenticateFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
public class SecurityCfg {

    private final JwtAuthenticateFilter jwtAuthenticateFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        return httpSecurity
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .httpBasic(http -> http.disable())
                .addFilterBefore(jwtAuthenticateFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(
                        http -> http
                                .requestMatchers("/auth/register", "/auth/login").anonymous()
                                .requestMatchers("/auth/refresh", "/auth/logout").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/users/**", "/notes/**").authenticated()
                                .anyRequest().permitAll())
                .exceptionHandling(
                        exp -> exp
                                .authenticationEntryPoint(
                                        (request, response, authException) -> {
                                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                            response.setContentType("application/json");
                                            response.getWriter().write("{\"status\":401,\"error\":\"unauthorized\"}");
                                        })
                                .accessDeniedHandler(
                                        (request, response, accessDeniedException) -> {
                                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                            response.setContentType("application/json");
                                            response.getWriter().write("{\"status\":\"403\",\"error\":\"forbidden\"}");
                                        })
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
