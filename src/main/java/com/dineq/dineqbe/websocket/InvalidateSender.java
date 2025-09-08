package com.dineq.dineqbe.websocket;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

@Component
@RequiredArgsConstructor
public class InvalidateSender {

    // 아직 클라이언트 ACK을 받지 못한 메시지를 보관하는 객체
    // payload: 실제 보낼 데이터 (messageId, status, 등)
    // attempts: 몇 번 재전송했는지 카운트
    private static class Pending {
        final Map<String, Object> payload;
        int attempts = 0;
        Pending(Map<String, Object> payload) { this.payload = payload; }
    }

    private static final String DESTINATION = "/topic/orders";
    private final SimpMessagingTemplate messaging;
    private final Map<String, Pending> pendings = new ConcurrentHashMap<>();
    private final ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();

    // 알림 형태 지정
    public void sendAlert(String status) {
        String id = UUID.randomUUID().toString();
        Map<String, Object> payload = Map.of(
                "type", "invalidate",
                "messageId", id,
                "status", status
        );
        pendings.put(id, new Pending(payload)); // 대기열 등록
        doSend(id);
    }

    // 실제 전송 & 재전송
    // InvalidateSender 안에서 실제 메시지를 클라이언트로 보내고, ACK이 안 오면 재전송
    private void doSend(String id) {
        Pending p = pendings.get(id);
        if (p == null) return;
        messaging.convertAndSend(DESTINATION, p.payload);
        ses.schedule(() -> {
            Pending now = pendings.get(id);
            if (now == null) return;            // ACK 옴
            if (now.attempts >= 5) {            // 최대 5회 후 포기
                pendings.remove(id);
                return;
            }
            now.attempts += 1;
            doSend(id);                         // 재전송
        }, retryDelaySeconds(p.attempts), TimeUnit.SECONDS);
    }

    // 재전송 간격
    // 지수 백오프 방식 (메세지 재전송/네트워크 재시도에서 많이 쓴다)
    // 네트워크 지연 같은 일시적 장애를 고려
    private long retryDelaySeconds(int attempts) {
        return (long) (3 * Math.pow(2, Math.max(0, attempts - 1))); // 3,3,6,12,24,48
    }

    // 클라이언트가 /app/ack 으로 messageId 를 보내면 실행됨
    // pendings 에서 해당 메시지 제거 → 재전송 중단
    public void onAck(String messageId) {
        Pending p = pendings.remove(messageId);
        if (p != null) {
            Object status = p.payload.get("status");
            System.out.println("✅ ACK 수신 - messageId=" + messageId + ", status=" + status);
        } else {
            System.out.println("⚠️ ACK 수신했지만 대기열에서 찾지 못함 - messageId=" + messageId);
        }
    }

    // 서버 내려갈 때 재전송 스케줄러 안전하게 종료
    @PreDestroy
    public void shutdown() { ses.shutdown(); }
}
