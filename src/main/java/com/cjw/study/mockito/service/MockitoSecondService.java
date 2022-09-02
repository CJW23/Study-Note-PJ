package com.cjw.study.mockito.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MockitoSecondService {
    public String getStr() {
        return "hello";
    }
    public void printStr() {
        log.info("hello");
    }

    public void printParam(String s) {
        log.info(s);
    }
}
