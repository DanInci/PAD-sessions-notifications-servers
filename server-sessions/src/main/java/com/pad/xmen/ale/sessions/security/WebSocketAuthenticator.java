package com.pad.xmen.ale.sessions.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 2019-05-21
 */
@Component
public class WebSocketAuthenticator{

    @Autowired
    private JwtUtil jwtUtil;

    // This method MUST return a UsernamePasswordAuthenticationToken instance, the spring security chain is testing it with 'instanceof' later on. So don't use a subclass of it or any other class
    UsernamePasswordAuthenticationToken getAuthenticatedOrFail(final String token) throws AuthenticationException {
        if (token != null && jwtUtil.validateToken(token)) {
            AuthCtx auth = jwtUtil.parseToken(token);
            SecurityContextHolder.getContext().setAuthentication(auth);

            return new UsernamePasswordAuthenticationToken(auth, null, Collections.singleton((GrantedAuthority) () -> "USER"));
        }
        throw new JwtAuthenticationException("Missing JWT token");
    }
}
