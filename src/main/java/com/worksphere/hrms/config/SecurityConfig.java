package com.worksphere.hrms.config;

import com.worksphere.hrms.security.CustomUserDetailsService;
import com.worksphere.hrms.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    // =========================
    // AUTHENTICATION PROVIDER
    // =========================

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }


    // =========================
    // SECURITY FILTER CHAIN
    // =========================

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http

                // =========================
                // CORS
                // =========================

                .cors(cors -> {})


                // =========================
                // CSRF
                // =========================

                .csrf(csrf -> csrf.disable())


                // =========================
                // SESSION
                // =========================

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )


                // =========================
                // AUTHORIZATION
                // =========================

                .authorizeHttpRequests(auth -> {

                    // -------------------------
                    // PUBLIC ENDPOINTS
                    // -------------------------

                    auth.requestMatchers(
                            "/api/auth/**",
                            "/api/health",
                            "/v3/api-docs/**",
                            "/swagger-ui/**",
                            "/swagger-ui.html"
                    ).permitAll();


                    // -------------------------
                    // DASHBOARD
                    // -------------------------

                    auth.requestMatchers(
                            "/api/dashboard/**"
                    ).hasAnyAuthority(
                            "ADMIN",
                            "MANAGER",
                            "EMPLOYEE"
                    );


                    // -------------------------
                    // EMPLOYEES
                    // -------------------------

                    auth.requestMatchers(
                            "/api/employees/**"
                    ).hasAnyAuthority(
                            "ADMIN",
                            "MANAGER"
                    );


                    // -------------------------
                    // DEPARTMENTS
                    // -------------------------

                    auth.requestMatchers(
                            "/api/departments/**"
                    ).hasAnyAuthority(
                            "ADMIN",
                            "MANAGER"
                    );


                    // -------------------------
                    // ATTENDANCE
                    // -------------------------

                    auth.requestMatchers(
                            "/api/attendance/**"
                    ).hasAnyAuthority(
                            "ADMIN",
                            "MANAGER",
                            "EMPLOYEE"
                    );


                    // -------------------------
                    // LEAVE APPROVAL
                    // -------------------------

                    auth.requestMatchers(
                            "/api/leaves/*/approve",
                            "/api/leaves/*/reject"
                    ).hasAnyAuthority(
                            "ADMIN",
                            "MANAGER"
                    );


                    // -------------------------
                    // LEAVE REQUESTS
                    // -------------------------

                    auth.requestMatchers(
                            "/api/leaves/**"
                    ).hasAnyAuthority(
                            "ADMIN",
                            "MANAGER",
                            "EMPLOYEE"
                    );


                    // -------------------------
                    // EVERYTHING ELSE
                    // -------------------------

                    auth.anyRequest().authenticated();

                })


                // =========================
                // AUTHENTICATION PROVIDER
                // =========================

                .authenticationProvider(
                        authenticationProvider()
                )


                // =========================
                // JWT FILTER
                // =========================

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );


        return http.build();
    }
}