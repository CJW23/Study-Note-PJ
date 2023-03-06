package com.cjw.study.jpa.lock.service;

import com.cjw.study.jpa.lock.entity.LockChild;
import com.cjw.study.jpa.lock.entity.LockParent;
import com.cjw.study.jpa.lock.repository.LockParentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.util.List;

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
    private final EntityManager em;

    @Transactional
    public void insertLockParent() {
        LockParent lockParent = LockParent.ofDefault();
        this.lockParentRepository.save(lockParent);
    }

    @Transactional
    public void insertLockChild(Long parentId) {
        LockParent lockParent = this.lockParentRepository.findById(parentId).orElseThrow(() -> new RuntimeException(""));
        lockParent.addLockChild(LockChild.ofDefault());
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

    /**
     * lockParent Version 증가
     * lockChild Version 증가 X
     */
    @Transactional
    public void findParentByIdForceIncrement(Long parentId) {
        LockParent lockParent = this.em.find(LockParent.class, parentId, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        List<LockChild> lockChildren = lockParent.getLockChildren();
    }

    /**
     * 모든 Parent Version 증가
     */
    @Transactional
    public void findAllForceIncrement() {
        List<LockParent> lockParents = this.lockParentRepository.findAll();
    }

    /**
     * fetch join LockParent Version 같이 증가
     */
    @Transactional
    public void findParentByIdWithChild(Long parentId) {
        LockParent lockParent = this.lockParentRepository.findByIdWithChild(parentId);
    }

    /**
     * 엔티티 값이 변경된 경우 Version 2 증가
     * 값을 업데이트 하면서 한번 증가
     * 트랜잭션이 끝나고 강제 한번 증가
     * 총 2번
     */
    @Transactional
    public void updateChildForceIncrement(Long parentId) {
        LockParent lockParent = this.lockParentRepository.findByIdForceIncrement(parentId);
        lockParent.plusCount();
    }
}
