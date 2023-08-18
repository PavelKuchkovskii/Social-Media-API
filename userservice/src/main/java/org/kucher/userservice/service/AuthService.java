package org.kucher.userservice.service;

import org.kucher.userservice.dao.api.IUserDao;
import org.kucher.userservice.dao.entity.User;
import org.kucher.userservice.security.auth.CustomUserDetails;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    private final IUserDao dao;

    public AuthService(IUserDao dao) {
        this.dao = dao;
    }

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
