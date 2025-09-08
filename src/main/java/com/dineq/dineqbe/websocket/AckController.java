package com.dineq.dineqbe.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AckController {

    private final InvalidateSender sender;

    public record AckMessage(String messageId) {}

    // 서버가 보낸 WebSocket 메시지가 클라이언트에 잘 도착했는지 확인하는 ACK 컨트롤러
    // ack를 수신하는 엔드포인트
    // InvalidateSender와 연계해서 ACK 받은 메시지를 대기열(pendings)에서 제거
    @MessageMapping("/ack")
    public void ack(@Payload AckMessage msg) {
        if (msg != null && msg.messageId() != null) {
            log.info("📩 클라이언트 ACK 수신: messageId={}", msg.messageId());
            sender.onAck(msg.messageId());
        } else {
            log.warn("⚠️ 잘못된 ACK 메시지 수신: {}", msg);
        }
    }
}
