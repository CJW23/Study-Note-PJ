package com.cjw.study.sse.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
@Slf4j
public class SSEContainer {
    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public SseEmitter add() {
        SseEmitter sseEmitter = new SseEmitter();

        sseEmitter.onTimeout(() -> {
            emitters.remove(sseEmitter);
            sseEmitter.complete();
        });

        sseEmitter.onCompletion(() -> emitters.remove(sseEmitter));

        emitters.add(sseEmitter);

        return sseEmitter;
    }

    public void send(String msg) {
        if(isEmpty(msg)) {
            return;
        }
        log.info(String.valueOf(this.emitters.size()));
        this.emitters.forEach(e -> {
            try {
                e.send(msg);
            } catch (IOException ex) {e.complete();}
        });
    }
}
