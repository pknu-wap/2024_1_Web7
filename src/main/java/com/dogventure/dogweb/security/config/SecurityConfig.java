package com.dogventure.dogweb.security.config;

import com.dogventure.dogweb.security.authProvider.JwtAuthProvider;
import com.dogventure.dogweb.security.authProvider.UsernamePasswordAuthProvider;
import com.dogventure.dogweb.security.filter.CustomUsernamePasswordAuthFilter;
import com.dogventure.dogweb.security.filter.JwtValidateFilter;
import com.dogventure.dogweb.security.handler.CustomSuccessHandler;
import com.dogventure.dogweb.security.handler.securityExHandler.AccessDeniedHandlerImpl;
import com.dogventure.dogweb.security.handler.securityExHandler.AuthenticationEntryPointImpl;
import com.dogventure.dogweb.security.service.Oauth2UserServiceImpl;
import com.dogventure.dogweb.security.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UsernamePasswordAuthProvider usernamePasswordAuthProvider;
    private final JwtAuthProvider jwtAuthProvider;

    private final AuthenticationEntryPointImpl authenticationEntryPoint;
    private final AccessDeniedHandlerImpl accessDeniedHandler;

    private final ObjectMapper objectMapper;
    private final JwtUtils jwtUtils;

    private final Oauth2UserServiceImpl oauth2UserService;
    private final CustomSuccessHandler customSuccessHandler;

    @Bean
    public AuthenticationManager authManagerProvider(HttpSecurity http) throws Exception {

        return http
                .getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(usernamePasswordAuthProvider)
                .authenticationProvider(jwtAuthProvider)
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {

        return http

                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)

                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);

                        configuration.setExposedHeaders(Collections.singletonList("Set-Cookie"));
                        configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                        return configuration;
                    }
                }))

                .sessionManagement(management -> management
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .exceptionHandling(exHandling -> exHandling
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler))

                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(oauth2UserService))
                        .successHandler(customSuccessHandler)
                )

                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/basic/signup").permitAll()
                        .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/basic/login","/api/map/**", "/api/gpt/**").hasAnyAuthority("USER", "ADMIN")
                        .anyRequest().authenticated())

                .addFilterBefore(new CustomUsernamePasswordAuthFilter(objectMapper, authManager), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JwtValidateFilter(jwtUtils, authManager), CustomUsernamePasswordAuthFilter.class)

                .build();
    }
}
