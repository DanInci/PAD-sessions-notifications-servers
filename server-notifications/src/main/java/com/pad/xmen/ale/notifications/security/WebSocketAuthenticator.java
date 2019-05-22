package com.pad.xmen.ale.notifications.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 2019-05-22
 */
@Component
public class WebSocketAuthenticator {

    @Autowired
    private JwtUtil jwtUtil;

    UsernamePasswordAuthenticationToken getAuthenticatedOrFail(final String token) throws AuthenticationException {
        if(!jwtUtil.validateToken(token)) {
            throw new JwtAuthenticationException("Invalid jwt token");
        }

        String sessionsServerName = jwtUtil.parseToken(token);
        return new UsernamePasswordAuthenticationToken(sessionsServerName, null, Collections.singleton((GrantedAuthority) () -> "SESSION-SERVER"));
    }
}
