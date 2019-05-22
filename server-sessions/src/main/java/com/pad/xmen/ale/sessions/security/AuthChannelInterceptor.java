package com.pad.xmen.ale.sessions.security;

import com.pad.xmen.ale.sessions.Application;
import com.pad.xmen.ale.sessions.extras.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 2019-05-21
 */
@Component
public class AuthChannelInterceptor implements ChannelInterceptor {

    @Autowired
    private WebSocketAuthenticator authenticator;

    @Override
    public Message<?> preSend(final Message<?> message, final MessageChannel channel) throws AuthenticationException {
        final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor.getCommand() == StompCommand.CONNECT) {
            final String authHeader = accessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION);

            if(authHeader != null && authHeader.length() > 7) {
                final String token = authHeader.substring(7);
                final UsernamePasswordAuthenticationToken user = authenticator.getAuthenticatedOrFail(token);

                final AuthCtx authCtx = (AuthCtx) user.getPrincipal();
                Application.log.info("Connection established from '" + authCtx.getName() + "'");
                accessor.setUser(user);
            }
            else {
                throw new JwtAuthenticationException("Authorization header is missing or its value is invalid");
            }
        }
        return message;
    }

}
