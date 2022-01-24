package com.example.helloworldwithjwt.controller;

import com.example.helloworldwithjwt.model.JwtRequest;
import com.example.helloworldwithjwt.model.JwtResponse;
import com.example.helloworldwithjwt.service.JwtUserDetailsService;
import com.example.helloworldwithjwt.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;
    private final ApplicationContext context;

    public JwtAuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, JwtUserDetailsService userDetailsService, ApplicationContext context) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.context = context;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Object> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) {
        if ( authenticate(jwtRequest.getUsername(), jwtRequest.getPassword()) ){
            final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
            final String token = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(token));
        }
        return ResponseEntity.internalServerError().body("AUTHENTICATION FAILED");
    }

    private boolean authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return true;
        } catch (DisabledException e) {
            context.getBean(Logger.class).info("USER_DISABLED");
        } catch (BadCredentialsException e) {
            context.getBean(Logger.class).info("INVALID_CREDENTIALS");
        }
        return false;
    }
}
