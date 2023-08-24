package org.kucher.userservice.service;

import org.kucher.userservice.repository.IUserRepository;
import org.kucher.userservice.model.User;
import org.kucher.userservice.security.auth.CustomUserDetails;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

/**
 * Implementation of the UserDetailsService interface, responsible for user authentication and authorization.
 * This service class integrates with Spring Security to provide user details for authentication purposes.
 */
@Service
public class AuthServiceImpl implements UserDetailsService {

    private final IUserRepository dao;

    public AuthServiceImpl(IUserRepository dao) {
        this.dao = dao;
    }

    /**
     * Loads user details by their username (email) for authentication.
     *
     * @param username The username (email) of the user to load details for.
     * @return UserDetails object representing the user's authentication details.
     * @throws UsernameNotFoundException if the user with the specified username is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> oUser = dao.findByEmail(username);
        if (oUser.isEmpty()) {
            throw new BadCredentialsException("Bad credentials");
        }

        User user = oUser.get();

        return new CustomUserDetails(user.getStatus(),
                                    Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())),
                                    user.getPassword(),
                                    user.getUuid().toString());

    }
 }
