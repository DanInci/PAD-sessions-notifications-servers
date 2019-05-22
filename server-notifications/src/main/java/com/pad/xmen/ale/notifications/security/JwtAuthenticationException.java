package com.pad.xmen.ale.notifications.security;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 2019-05-22
 */
class JwtAuthenticationException extends AuthenticationException {
    JwtAuthenticationException(String msg) {
        super(msg);
    }
}
