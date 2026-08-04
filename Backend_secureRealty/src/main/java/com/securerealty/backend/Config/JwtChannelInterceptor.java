package com.securerealty.backend.Config;

import com.securerealty.backend.Service.CustomUserDetailsService;
import com.securerealty.backend.Service.JwtService;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtChannelInterceptor(
            JwtService jwtService,
            CustomUserDetailsService userDetailsService) {

        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        System.out.println("========== INTERCEPTOR ==========");
        System.out.println("Command: " + accessor.getCommand());

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            System.out.println("CONNECT frame received");

            String authHeader = accessor.getFirstNativeHeader("Authorization");

            System.out.println("Authorization Header: " + authHeader);

            if (authHeader != null && authHeader.startsWith("Bearer ")) {

                String jwt = authHeader.substring(7);

                System.out.println("JWT: " + jwt);

                String username = jwtService.extractUsername(jwt);

                System.out.println("Username: " + username);

                UserDetails user =
                        userDetailsService.loadUserByUsername(username);

                if (jwtService.validateToken(jwt, user)) {

                    System.out.println("JWT Valid");

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    user,
                                    null,
                                    user.getAuthorities());

                    accessor.setUser(authentication);
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    System.out.println("Principal attached");
                }
            }
        }

        return message;
    }
}