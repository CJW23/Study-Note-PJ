package com.cjw.study.jpa.repository;

import com.cjw.study.jpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TestRepository extends JpaRepository<Member, Long> {
    @Query(value = "SELECT COUNT(1) FROM member", nativeQuery = true)
    Long findCountMember();
}
