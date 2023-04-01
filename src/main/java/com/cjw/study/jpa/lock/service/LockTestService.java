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
    public void updateParentForceIncrement(Long parentId) {
        LockParent lockParent = this.lockParentRepository.findByIdForceIncrement(parentId);
        lockParent.plusCount();
    }

    /**
     * PESSIMISTIC_READ 모드로 LockParent 읽은 후
     * 0.5초 대기후 count Update
     *
     * 1. Transaction1 Select For Shared 구문 수행
     * 2. Transaction2 Select For Shared 구문 수행
     * 3. Transaction1 Update 수행 (Transaction2의 Shared Lock으로 인해 대기)
     * 4. Transaction2 Update 수행 (Transaction1의 Shared Lock으로 인해 대기)
     * 결과: 데드락 발생
     */
    @Transactional
    public void findByParentIdPessimisticReadAndUpdate(Long parentId) {
        LockParent lockParent = this.lockParentRepository.findByIdPessimisticRead(parentId);
        //0.5초 대기
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        lockParent.plusCount();
    }

    /**
     * PESSIMISTIC_WRITE 모드로 LockParent 읽은 후
     * 0.5초 대기후 count Update
     *
     * 1.Transaction1 Select For Update 구문 수행
     * 2.Transaction2 Select For Update 구문 수행 (Transaction1 X-Lock으로 인해 대기)
     * 3.Transaction3 Select For Update 구문 수행 (Transaction1 X-Lock으로 인해 대기)
     * 4.Transaction1 Update 수행(X-lock 해제)
     * 5.Transaction2 Update 수행
     * 6.Transaction3 Update 수행
     * 결과: 3개의 트랜잭션 정상 처리
     */
    @Transactional
    public void findByParentIdPessimisticWriteAndUpdate(Long parentId) {
        LockParent lockParent = this.lockParentRepository.findByIdPessimisticWrite(parentId);
        //0.5초 대기
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        lockParent.plusCount();
    }

    @Transactional
    public void findByParentByIdWithChildPessimisticForceIncrement(Long parentId) {
        LockParent lockParent = this.lockParentRepository.findByIdPessimisticForceIncrement(parentId);
    }
}
