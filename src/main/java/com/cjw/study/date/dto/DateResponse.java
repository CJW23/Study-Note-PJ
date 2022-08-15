package com.cjw.study.date.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateResponse {
    @Getter
    @AllArgsConstructor
    @Builder
    public static class Default {
        private LocalDateTime defaultDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime jsonFormatDate;
        @DateTimeFormat(pattern = "yy-MM-dd HH:mm:ss")
        private LocalDateTime dateFormatDate;
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        private LocalDateTime serializeDate;
        public static DateResponse.Default of() {
            String str = "2000-04-08 24:05:00";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
            return DateResponse.Default.builder()
                    .defaultDate(LocalDateTime.now())
                    .jsonFormatDate(LocalDateTime.now())
                    .dateFormatDate(LocalDateTime.now())
                    .serializeDate(dateTime).build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class JsonFormatRequest {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime date;
    }
}
