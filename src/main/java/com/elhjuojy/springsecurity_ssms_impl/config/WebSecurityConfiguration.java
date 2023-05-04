package com.elhjuojy.springsecurity_ssms_impl.config;

import com.elhjuojy.springsecurity_ssms_impl.filters.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity

class WebSecurityConfiguration {

    // Custom filter
    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    // Custom UserDetailsService
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void configurePasswordEncoder(AuthenticationManagerBuilder builder) throws Exception {
        // adding custom UserDetailsService and encryption bean to Authentication Manager
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }



    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }


    @Bean
   protected  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("we are configured");
      try {
          http
                  // disabling csrf since we won't use form login
                  .csrf().disable()
                  // giving every permission to every request for /login endpoint
                  .authorizeHttpRequests((login)->
                          login.requestMatchers("/login").permitAll())
                  .authorizeHttpRequests((authz) -> authz
                          .anyRequest().authenticated()
                  )

                  // setting stateless session, because we choose to implement Rest API
                  .sessionManagement()
                  .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

          // adding the custom filter before UsernamePasswordAuthenticationFilter in the filter chain
          http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
          return http.build();
      }catch (Exception e){
          throw new Exception(e.toString());
      }
   }
}