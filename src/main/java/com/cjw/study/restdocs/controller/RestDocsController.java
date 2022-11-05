package com.cjw.study.restdocs.controller;

import com.cjw.study.restdocs.dto.RestDocsDto;
import com.cjw.study.restdocs.dto.RestDocsDto.Test;
import com.cjw.study.restdocs.dto.RestDocsDto.Test2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.cjw.study.restdocs.dto.RestDocsDto.*;

@RestController
@RequestMapping(value = "/rest-docs")
public class RestDocsController {

    @GetMapping("/test1")
    public ResponseEntity<Test> test1(@ModelAttribute Test test) {
        return ResponseEntity.ok(test);
    }

    @GetMapping("/test2")
    public ResponseEntity<Test2> test2() {
        Test3 test3 = Test3.builder()
                .a("a test")
                .b("b test")
                .c("c test").build();
        Test4 test4 = Test4.builder().test4test3(test3).build();
        Test2 test2 = Test2.builder()
                .test3(test3)
                .tests(List.of(test3))
                .test4(test4)
                .build();

        return ResponseEntity.ok().body(test2);
    }
}
