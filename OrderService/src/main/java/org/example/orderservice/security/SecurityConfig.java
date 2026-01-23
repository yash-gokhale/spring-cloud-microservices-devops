package org.example.orderservice.security;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {

//    @Value("${security.jwt.enabled:true}")
//    private boolean jwtEnabled;

    @Value(("${security.api-key.enabled}"))
    private boolean apiKeyEnabled;

//    private final ObjectProvider<JwtAuthenticationFilter> jwtFilterProvider;

//    public SecurityConfig(ObjectProvider<JwtAuthenticationFilter> jwtFilterProvider) {
//        this.jwtFilterProvider = jwtFilterProvider;
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, ApiKeyAuthFilter apiKeyAuthFilter) throws Exception {

        System.out.println(">>> SecurityConfig FILTER CHAIN LOADED <<<");

        // ✅ ALWAYS disable CSRF for REST APIs
        http.csrf(AbstractHttpConfigurer::disable);

        if(apiKeyEnabled){
            http
                    .sessionManagement(sm ->
                            sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/actuator/**").permitAll()
                            .anyRequest().permitAll()
                    )
                    .addFilterBefore(apiKeyAuthFilter, UsernamePasswordAuthenticationFilter.class);

//            return http.build();
        }

//        if (!jwtEnabled) {
//            http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
//            return http.build();
//        }
//
//        JwtAuthenticationFilter jwtFilter = jwtFilterProvider.getIfAvailable();
//
//        http.authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/auth/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .sessionManagement(sess ->
//                        sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                );
//
//        if (jwtFilter != null) {
//            http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//        }
//
        return http.build();
    }
}
