package com.cjw.study.jpa.lock.repository;

import com.cjw.study.jpa.lock.entity.LockParent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

@Repository
public interface LockParentRepository extends JpaRepository<LockParent, Long> {
    /**
     * OPTIMISTIC 모드로 조회
     */
    @Override
    @Lock(LockModeType.OPTIMISTIC)
    Optional<LockParent> findById(Long id);

    @Override
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    List<LockParent> findAll();

    @Query("SELECT lp FROM LockParent lp join fetch lp.lockChildren where lp.parentId = :parentId")
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    LockParent findByIdWithChild(@Param("parentId") Long parentId);

    @Query("SELECT lp FROM LockParent lp WHERE lp.parentId = :parentId")
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    LockParent findByIdForceIncrement(@Param("parentId") Long parentId);

    @Query("SELECT lp FROM LockParent lp where lp.parentId = :parentId")
    @Lock(LockModeType.PESSIMISTIC_READ)
    LockParent findByIdPessimisticRead(@Param("parentId") Long parentId);

    @Query("SELECT lp FROM LockParent lp where lp.parentId = :parentId")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    LockParent findByIdPessimisticWrite(@Param("parentId") Long parentId);

    @Query("SELECT lp FROM LockParent lp join fetch lp.lockChildren where lp.parentId = :parentId")
    @Lock(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
    LockParent findByIdPessimisticForceIncrement(@Param("parentId") Long parentId);
}

