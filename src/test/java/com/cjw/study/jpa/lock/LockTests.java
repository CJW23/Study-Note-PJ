package com.cjw.study.jpa.lock;

import com.cjw.study.jpa.lock.service.LockTestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;

@SpringBootTest
public class LockTests {
    @Autowired
    LockTestService lockTestService;

    @Test
    void Parent_삽입() {
        lockTestService.insertLockParent();
    }

    @Test
    void Lock_None_동시_업데이트() {
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> {
            this.lockTestService.plusCount(1L);
        });
        CompletableFuture<Void> task2 = CompletableFuture.runAsync(() -> {
            this.lockTestService.plusCount(1L);
        });

        CompletableFuture.allOf(task1, task2).join();
    }

    @Test
    void Lock_Optimistic_동시_조회_업데이트() {
        CompletableFuture<Void> task2 = CompletableFuture.runAsync(() -> {
            this.lockTestService.findParentById(1L);
        });
        CompletableFuture<Void> task3 = CompletableFuture.runAsync(() -> {
            this.lockTestService.plusCount(1L);
        });

        CompletableFuture.allOf(task2, task3).join();
    }
}
