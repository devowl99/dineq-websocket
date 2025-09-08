package com.dineq.dineqbe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트가 웹소켓 연결을 맺을 엔드포인트 지정 (ws://서버/ws)
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*"); // 운영에선 특정 도메인만 허용 권장
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 구독 채널 (publish-subscribe)
        registry.enableSimpleBroker("/topic"); // 백 -> 프론트

        // 서버 처리 (주문 ack 등)
        registry.setApplicationDestinationPrefixes("/app"); // 프론트 -> 백
    }
}