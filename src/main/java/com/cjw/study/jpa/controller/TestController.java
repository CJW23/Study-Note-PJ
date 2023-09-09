package com.cjw.study.jpa.controller;

import com.cjw.study.jpa.entity.Member;
import com.cjw.study.jpa.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Mysql
 */
@RestController
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TestController {
    @PersistenceContext
    private final EntityManager em;
    private final TestRepository testRepository;

    @GetMapping(value = "/test")
    @Transactional
    public ResponseEntity<String> test() {
        Member member = new Member("test", 123);
        em.persist(member);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        em.flush();
        return ResponseEntity.ok("Success");
    }

    @GetMapping(value = "/test2")
    @Transactional
    public ResponseEntity<String> test2() {
        for (int i=0; i<1000; i++) {
            testRepository.findCountMember();
        }
        return ResponseEntity.ok("Success");
    }
}
