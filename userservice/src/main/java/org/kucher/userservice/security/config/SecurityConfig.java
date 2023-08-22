package org.kucher.userservice.security.config;

import org.kucher.userservice.security.auth.CustomDaoAuthenticationProvider;
import org.kucher.userservice.security.jwt.filter.JwtFilter;
import org.kucher.userservice.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final JwtFilter filter;


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new CustomDaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(authService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    public SecurityConfig(AuthService authService, PasswordEncoder passwordEncoder, JwtFilter filter) {
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
        this.filter = filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
             .sessionManagement()
             .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
             .and();

        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/users/login", "/users/registration").permitAll()
                .antMatchers("/users/me").authenticated()
                //.antMatchers("/users/*").hasRole("USER") //РАБОТАЕТ!!!
                .and()
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

}