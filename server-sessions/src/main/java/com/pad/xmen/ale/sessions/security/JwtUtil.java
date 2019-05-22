package com.pad.xmen.ale.sessions.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.UUID;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 2019-05-21
 */
@Component
public class JwtUtil {

    @Value("${jwt.server.secret-key}")
    private String serverSecretKey;

    @Value("${jwt.users.secret-key}")
    private String usersSecretKey;

    @Value("${jwt.users.expire-length:1800000}")
    private long usersTokenValidity;

    @Value("${server.name}")
    private String serverName;

    private String serverToken;

    @PostConstruct
    private void generateServerToken() {
        Claims claims = Jwts.claims().setSubject(serverName);

        this.serverToken = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, serverSecretKey)
                .compact();
    }

    AuthCtx parseToken(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(usersSecretKey)
                    .parseClaimsJws(token)
                    .getBody();

            AuthCtx ctx = new AuthCtx();
            ctx.setName(body.getSubject());
            ctx.setRoomId(UUID.fromString((String)body.get("roomId")));
            ctx.setOwner((Boolean) body.get("isOwner"));

            Date expireDate = new Date((long) body.get("expiresAt"));
            ctx.setExpiresAt(expireDate);

            return ctx;

        } catch (JwtException | ClassCastException e) {
            return null;
        }
    }

    public String generateUsersToken(String name, UUID roomId, Boolean isOwner) {
        Claims claims = Jwts.claims().setSubject(name);
        claims.put("roomId", roomId);
        claims.put("isOwner", isOwner);

        Date now = new Date();
        Date validUntil = new Date(now.getTime() + usersTokenValidity);

        claims.put("expiresAt", validUntil);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(validUntil)
                .signWith(SignatureAlgorithm.HS512, usersSecretKey)
                .compact();
    }

    public String getServerToken() {
        return this.serverToken;
    }

    boolean validateToken(String token) throws JwtAuthenticationException {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(usersSecretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("Invalid JWT token");
        }
    }

}
