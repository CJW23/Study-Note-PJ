package com.cjw.study.restdocs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public class RestDocsDto {
    @Builder
    @AllArgsConstructor
    @Getter
    public static class Test {
        private String param1;
        private int param2;
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime param3;
    }

    @Builder 
    @AllArgsConstructor
    @Getter
    public static class Test2 {
        private Test3 test3;
        private List<Test3> tests;
        private Test4 test4;
    }
    @Builder
    @AllArgsConstructor
    @Getter
    public static class Test3 {
        private String a;
        private String b;
        private String c;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Test4 {
        private Test3 test4test3;
    }
}
