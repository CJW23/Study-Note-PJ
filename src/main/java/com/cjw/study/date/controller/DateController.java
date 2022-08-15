package com.cjw.study.date.controller;

import com.cjw.study.date.dto.DateResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class DateController {
    /**
     * 결과
     * {
     *   "defaultDate": "2022-08-15T22:28:47.852586",
     *   "jsonFormatDate": "2022-08-15 22:28:47",
     *   "dateFormatDate": "2022-08-15T22:28:47.852621",
     *   "serializeDate": "2022-08-15T22:28:47.85263"
     * }
     * Response(Java->Json)할 경우 @DateTimeFormat이 작동하지 않는 것을 확인 할 수 있음
     */
    @GetMapping("/date")
    public ResponseEntity<DateResponse.Default> getDate() {
        return ResponseEntity.ok(DateResponse.Default.of());
    }

    /**
     22-10-22 10:00:00 형태로 param 전달시 정상적으로 바인딩
     */
    @GetMapping("/date-time-format")
    public ResponseEntity<LocalDateTime> dateTimeFormat(@RequestParam @DateTimeFormat(pattern = "yy-MM-dd HH:mm:ss") LocalDateTime date) {
        return ResponseEntity.ok(date);
    }

    /**
     QueryString으로 받을 시 @JsonFormat은 작동하지 않음
     @DateFormat은 작동
     */
    @GetMapping("/json-format")
    public ResponseEntity<LocalDateTime> jsonFormat(DateResponse.JsonFormatRequest date) {
        return ResponseEntity.ok(date.getDate());
    }

    /**
     * body로 받을 시 @JsonFormat 작동
     */
    @PostMapping("/json-format-post")
    public ResponseEntity<DateResponse.JsonFormatRequest> jsonFormatPost(@RequestBody DateResponse.JsonFormatRequest date) {
        return ResponseEntity.ok(date);
    }

    /**
     * 결론
     * Request GET에서 queryString으로 날짜를 받을 경우에만 @DateFormat사용
     * RequestBody로 받는 경우 @JsonFormat 사용
     * Response에도 @DateFormat은 작동하지 않기에 @JsonFormat 사용
     */
}
