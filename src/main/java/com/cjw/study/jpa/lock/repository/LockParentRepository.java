package com.cjw.study.jpa.lock.repository;

import com.cjw.study.jpa.lock.entity.LockParent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LockParentRepository extends JpaRepository<LockParent, Long> {
}
