package com.coffeeinfinitive.config;

import com.coffeeinfinitive.security.*;
import com.coffeeinfinitive.service.UserOrgService;
import com.coffeeinfinitive.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.MultipartConfigElement;
import java.util.Arrays;


/**
 * Created by jinz on 4/15/17.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String LOGIN_ENTRY_POINT = "/api/v1/login";

    @Autowired
    private UserService userService;

    @Autowired
    private UserOrgService userOrgService;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CoffeeRestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    CoffeeAuthenticationProvider coffeeAuthenticationProvider;


    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        // Tạo user mặc định lưu trong bộ nhớ
        auth.inMemoryAuthentication()
                .withUser("admin").password("admin").roles("ADMIN");
        auth.inMemoryAuthentication()
                .withUser("admin1").password("admin").roles("ADMIN");

        auth.authenticationProvider(coffeeAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint);
        http.cors();

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/password").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.POST, LOGIN_ENTRY_POINT).permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/activities/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/organizations/**").permitAll()
                .antMatchers(HttpMethod.GET, "/files/**").permitAll()
                .anyRequest().authenticated()
                .and()
                // We filter the api/login requests
                .addFilterBefore(new CoffeeJwtLoginFilter(LOGIN_ENTRY_POINT,tokenAuthenticationService,
                                userService, authenticationManager(), userOrgService),
                        UsernamePasswordAuthenticationFilter.class)
                // And filter other requests to check the presence of JWT in header
                .addFilterBefore(new CoffeeJwtAuthenticationFilter(tokenAuthenticationService),
                        UsernamePasswordAuthenticationFilter.class);
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","OPTIONS","DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("X-Authorization","Content-Type", "enctype"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
