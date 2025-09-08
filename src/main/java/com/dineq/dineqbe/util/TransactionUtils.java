package com.dineq.dineqbe.util;

import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class TransactionUtils {

    private TransactionUtils() {} // 유틸 클래스라서 생성자 막기

    // 트랜잭션 유틸 클래스 따로 분리

    // 웹소켓 알림은 DB 트랜잭션이 정상적으로 커밋된 후 나가야 한다. (롤백되었는데 주문 알림을 보내면 큰일남)
    // 바로 sendAlert를 쓰는게 아닌, afterCommit을 이용해 트랜잭션 커밋 이후 알림을 쏘도록 함. (안전 제일)
    public static void afterCommit(Runnable r) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                r.run();
            }
        });
    }
}
