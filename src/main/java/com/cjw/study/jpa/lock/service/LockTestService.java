package com.cjw.study.jpa.lock.service;

import com.cjw.study.jpa.lock.entity.LockParent;
import com.cjw.study.jpa.lock.repository.LockParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * None:
 *
 * Optimistic:
 *
 * Optimistic_Force_Increment:
 *
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LockTestService {
    private final LockParentRepository lockParentRepository;

    @Transactional
    public void insertLockParent() {
        LockParent lockParent = LockParent.ofDefault();
        this.lockParentRepository.save(lockParent);
    }

    @Transactional
    public void plusCount(Long parentId) {
        LockParent lockParent = this.lockParentRepository.findById(parentId).orElseThrow(() -> new RuntimeException(""));
        lockParent.plusCount();
    }
}
