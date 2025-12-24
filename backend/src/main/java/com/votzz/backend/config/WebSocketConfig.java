package com.votzz.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Endpoint de conexão para o SockJS
        registry.addEndpoint("/ws-votzz")
                .setAllowedOriginPatterns("http://localhost:5173", "http://localhost:3000", "*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Onde o front se inscreve para receber (/topic/dashboard, /topic/assembly/ID)
        registry.enableSimpleBroker("/topic");
        // Prefixo para o front enviar mensagens ao servidor
        registry.setApplicationDestinationPrefixes("/app");
    }
}