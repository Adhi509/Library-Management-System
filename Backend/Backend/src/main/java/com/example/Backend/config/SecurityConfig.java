//package com.example.Backend.config;

//public class SecurityConfig {

//}
// Put this file in: src/main/java/com/example/myproject/config/SecurityConfig.java

/*package com.example.Backend.config;

import com.example.Backend.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Marks this as a configuration class
@EnableWebSecurity // Enables Spring Security's web security support
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    // --- BEAN #1: The PasswordEncoder ---
    // This bean is used to hash passwords for storage
    // and to verify them during authentication.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // --- BEAN #2: The AuthenticationProvider ---
    // This bean tells Spring Security to use our CustomUserDetailsService
    // and our PasswordEncoder.
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // --- BEAN #3: The AuthenticationManager ---
    // This bean is required for the login endpoint to authenticate users.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // --- BEAN #4: The SecurityFilterChain ---
    // This is the main configuration for all your API security.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Disable CSRF protection. This is common for stateless REST APIs
            //    that use tokens for auth instead of sessions/cookies.
            .csrf(csrf -> csrf.disable())

            // 2. Configure authorization rules
            .authorizeHttpRequests(authz -> authz
                // These are your PUBLIC endpoints
                .requestMatchers("/api/auth/**").permitAll() // Allow all requests to /api/auth/ (register, login)
                
                // All other requests must be authenticated
                .anyRequest().authenticated()
            )

            // 3. Set session management to STATELESS
            //    This tells Spring Security not to create or use HTTP sessions.
            //    Every request will need to be re-authenticated (e.g., with a JWT).
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // 4. Tell Spring Security to use the AuthenticationProvider we defined
            .authenticationProvider(authenticationProvider());
            
            // We will add the JWT filter here in the next step.

        return http.build();
    }
} */
//In: src/main/java/com/example/Backend/config/SecurityConfig.java

 package com.example.Backend.config;

import com.example.Backend.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration; // <-- NEW IMPORT
import org.springframework.web.cors.CorsConfigurationSource; // <-- NEW IMPORT
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; // <-- NEW IMPORT

import java.util.Arrays; // <-- NEW IMPORT

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // --- BEAN #5: CORS Configuration ---
    // This bean defines the rules for Cross-Origin requests
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // This is your frontend's URL
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Cache-Control"));
        configuration.setAllowCredentials(true); // Important for cookies/sessions, but also good for JWT
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration); // Apply this config to all /api/ paths
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. ADD THE CORS CONFIGURATION
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // 2. Disable CSRF (you already have this)
            .csrf(csrf -> csrf.disable())

            // 3. Configure authorization rules (you already have this)
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/books").hasAnyRole("ADMIN", "LIBRARIAN")
                .requestMatchers(HttpMethod.PUT, "/api/books/**").hasAnyRole("LIBRARIAN", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/books/**").hasAnyRole("LIBRARIAN", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/books/**").authenticated()
                .anyRequest().authenticated()
            )

            // 4. Set session management to STATELESS (you already have this)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authenticationProvider(authenticationProvider());
            
        return http.build();
    }
}