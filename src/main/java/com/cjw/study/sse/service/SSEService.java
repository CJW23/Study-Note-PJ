package com.cjw.study.sse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class SSEService {
    private final SSEContainer container;

    public SseEmitter addChattingUser() {
        SseEmitter emitter = this.container.add();
        return emitter;
    }

    public String sendMessage(String msg) {
        this.container.send(msg);
        return "Success";
    }
}
