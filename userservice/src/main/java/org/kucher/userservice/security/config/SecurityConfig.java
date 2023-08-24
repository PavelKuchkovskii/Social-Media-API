package org.kucher.userservice.security.config;

import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import org.kucher.userservice.security.auth.CustomDaoAuthenticationProvider;
import org.kucher.userservice.security.jwt.filter.JwtFilter;
import org.kucher.userservice.service.AuthServiceImpl;
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

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final AuthServiceImpl authServiceImpl;
    private final JwtFilter filter;


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new CustomDaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(authServiceImpl);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }
    @Bean
    public SecretManagerServiceClient secretManagerServiceClient() throws IOException {
        return SecretManagerServiceClient.create();
    }


    public SecurityConfig(AuthServiceImpl authServiceImpl, PasswordEncoder passwordEncoder, JwtFilter filter) {
        this.passwordEncoder = passwordEncoder;
        this.authServiceImpl = authServiceImpl;
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
                .antMatchers("/users/admin/**").hasRole("ADMIN")
                .and()
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

}