package com.pad.xmen.ale.sessions.security;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 2019-05-21
 */
class JwtAuthenticationException extends AuthenticationException {

    JwtAuthenticationException(String message) {
        super(message);
    }

}