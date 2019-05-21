package com.pad.xmen.ale.sessions.security;

import org.springframework.beans.factory.annotation.Autowired;
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

    private final String X_AUTH_TOKEN = "X-AUTH-TOKEN";

    @Autowired
    private WebSocketAuthenticator authenticator;

    @Override
    public Message<?> preSend(final Message<?> message, final MessageChannel channel) throws AuthenticationException {
        final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT == accessor.getCommand()) {
            final String token = accessor.getFirstNativeHeader(X_AUTH_TOKEN);

            final UsernamePasswordAuthenticationToken user = authenticator.getAuthenticatedOrFail(token);

            accessor.setUser(user);
        }
        return message;
    }

}
