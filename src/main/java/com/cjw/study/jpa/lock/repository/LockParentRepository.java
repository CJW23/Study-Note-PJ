package com.cjw.study.jpa.lock.repository;

import com.cjw.study.jpa.lock.entity.LockParent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface LockParentRepository extends JpaRepository<LockParent, Long> {
    /**
     * OPTIMISTIC 모드로 조회
     */
    @Override
    @Lock(LockModeType.OPTIMISTIC)
    Optional<LockParent> findById(Long id);
}
