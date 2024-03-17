package com.cjw.study.aws.s3;

import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/s3")
public class S3Controller {
    private final S3Service s3Service;

    @GetMapping("/{key}")
    public ResponseEntity<byte[]> getImage(@PathVariable("key") String key) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(s3Service.getS3Image(key));
    }

    @PostMapping("")
    public ResponseEntity<String> saveImage(MultipartFile image) {
        s3Service.saveS3Image(image);
        return ResponseEntity.ok("SUCCESS UPLOAD");
    }
}
