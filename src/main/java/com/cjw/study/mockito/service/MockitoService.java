package com.cjw.study.mockito.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MockitoService {
    private final MockitoSecondService mockitoSecondService;

    public String getPrintAndGet() {
        log.info("hello");
        return "hello";
    }

    public String getSecondStr() {
        return this.mockitoSecondService.getStr();
    }

    public int getInt() {
        return 1;
    }

    public Integer getWrapperInteger() {
        return 1;
    }

    public List<String> getList() {
        return new ArrayList<>();
    }

    public String getString() {
        return "hello";
    }

    public void printSecondStr() {
        this.mockitoSecondService.printStr();
    }
}
