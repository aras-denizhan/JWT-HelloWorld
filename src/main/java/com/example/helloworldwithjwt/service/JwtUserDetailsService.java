package com.example.helloworldwithjwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final ApplicationContext context;

    @Autowired
    public JwtUserDetailsService(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.equals("daras")){
            return new User("daras", context.getBean(BCryptPasswordEncoder.class).encode("123456") , new ArrayList<>());
        }
        throw new UsernameNotFoundException("Username with: " + username + " is not found.");
    }
}
