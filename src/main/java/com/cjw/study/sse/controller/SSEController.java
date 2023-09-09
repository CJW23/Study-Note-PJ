package com.cjw.study.sse.controller;

import com.cjw.study.sse.service.SSEService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/api/v1/sse")
public class SSEController {
    private final SSEService sseService;

    @GetMapping(value = "/add")
    public ResponseEntity<SseEmitter> addChattingUser() {
        return ResponseEntity.ok(this.sseService.addChattingUser());
    }

    @GetMapping(value = "/send")
    public ResponseEntity<String> sendMessage(@RequestParam("msg") String msg) {
        return ResponseEntity.ok(this.sseService.sendMessage(msg));
    }
}
