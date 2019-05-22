package com.pad.xmen.ale.notifications.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 2019-05-22
 */
@Component
public class JwtUtil {

    @Value("${jwt.server.secret-key}")
    private String serverSecretKey;

    String parseToken(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(serverSecretKey)
                    .parseClaimsJws(token)
                    .getBody();

            return body.getSubject();

        } catch (JwtException | ClassCastException e) {
            return null;
        }
    }

    boolean validateToken(String token) throws JwtAuthenticationException {
        try {
            Jwts.parser().setSigningKey(serverSecretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("Expired or invalid JWT token");
        }
    }
}
