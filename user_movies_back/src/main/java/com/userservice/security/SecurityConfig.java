package com.userservice.security;

import com.userservice.filter.CustomAuthenticationFilter;
import com.userservice.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());

        // User's end points
        http.authorizeRequests().antMatchers(POST,"/login/**", "/token/refresh/**").permitAll();
        http.authorizeRequests().antMatchers(POST, "/user/save/**").permitAll();
        http.authorizeRequests().antMatchers(GET, "/user/get_all/**").permitAll();
        http.authorizeRequests().antMatchers(DELETE, "/user/delete/{id}/**").hasAnyAuthority("ROLE_SUPER_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/user/role/add_to_user/**").hasAnyAuthority("ROLE_ADMIN","ROLE_SUPER_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/user/get/{user}/**").hasAnyAuthority("ROLE_ADMIN","ROLE_SUPER_ADMIN","ROLE_MANAGER");
        http.authorizeRequests().antMatchers(PUT, "/user/update/{id}/**").hasAnyAuthority("ROLE_ADMIN","ROLE_SUPER_ADMIN","ROLE_MANAGER");
        http.authorizeRequests().antMatchers(PUT, "/user/update/{id}/**").hasAnyAuthority("ROLE_ADMIN","ROLE_SUPER_ADMIN","ROLE_MANAGER");
        http.authorizeRequests().antMatchers(PUT, "/user/update/{id}/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/user/**").hasAnyAuthority("ROLE_USER");


        // Movie's end points
        http.authorizeRequests().antMatchers("/movie/**").permitAll();
        http.authorizeRequests().antMatchers(GET,"/movie/get_all/**").permitAll();
        http.authorizeRequests().antMatchers(GET,"/movie/get/{id}/**").permitAll();
        http.authorizeRequests().antMatchers(POST,"/movie/save/**").permitAll();
        http.authorizeRequests().antMatchers(PUT,"/movie/update/{id}/**").hasAnyAuthority("ROLE_ADMIN","ROLE_SUPER_ADMIN");
        http.authorizeRequests().antMatchers(DELETE,"/movie/delete/{id}/**").hasAnyAuthority("ROLE_SUPER_ADMIN");


        // customAuthenticationFilter.setFilterProcessesUrl("/login");

        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(STATELESS);

        http.authorizeRequests().anyRequest().authenticated();

        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
