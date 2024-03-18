package com.fiap.ms_user.authetication.config.security;


import com.fiap.ms_user.authetication.config.security.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {

    @Autowired
    private AuthenticationProvider daoAuthProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        SecurityFilterChain securityFilterChain = http
                .csrf( csrfConfig ->  csrfConfig.disable())
                .sessionManagement( seesMagConfi -> seesMagConfi.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(daoAuthProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authReqConfig -> {

                    authReqConfig.requestMatchers(HttpMethod.POST, "/setting/customers").permitAll();
                    authReqConfig.requestMatchers(HttpMethod.POST, "setting/administrator/cadastra_hole").permitAll();
                    authReqConfig.requestMatchers(HttpMethod.POST, "setting/administrator/cadastra_hole_assistent").permitAll();
                    authReqConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
                    authReqConfig.requestMatchers(HttpMethod.GET, "/auth/validate-token").permitAll();

                    authReqConfig.anyRequest().authenticated();
                })
                .build();

        return securityFilterChain;
    }



}
