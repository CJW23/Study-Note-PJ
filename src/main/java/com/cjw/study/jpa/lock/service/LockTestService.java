package com.cjw.study.jpa.lock.service;

import com.cjw.study.jpa.lock.entity.LockParent;
import com.cjw.study.jpa.lock.repository.LockParentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * None:
 *      엔티티를 수정하는 시점에 버전을 증가 시킴 -> 이때 엔티티 버전이 조회 시점과 다르다면 예외 발생
 * Optimistic:
 *
 * Optimistic_Force_Increment:
 *
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class LockTestService {
    private final LockParentRepository lockParentRepository;
    @Transactional
    public void insertLockParent() {
        LockParent lockParent = LockParent.ofDefault();
        this.lockParentRepository.save(lockParent);
    }

    /**
     * LockParent count 증가
     */
    @Transactional
    public void plusCount(Long parentId) {
        LockParent lockParent = this.lockParentRepository.findById(parentId).orElseThrow(() -> new RuntimeException(""));
        lockParent.plusCount();
        log.info("\nplusCount 종료");
    }

    /**
     * LockParent 조회
     */
    public void findParentById(Long parentId) {
        this.lockParentRepository.findById(parentId);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("\nfindParentById 종료");
    }
}
