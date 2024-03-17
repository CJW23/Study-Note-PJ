package com.cjw.study.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    //디플트 빈 AmazonS3Client
    private final AmazonS3 amazonS3;
    @Value("${aws.bucketName}")
    private String bucketName;
    private final static String PREFIX = "images/";

    public void saveS3Image(MultipartFile imagesFile) {
        try {
            amazonS3.putObject(bucketName, PREFIX + UUID.randomUUID() + ".png", imagesFile.getInputStream(), createObjectMetadata(imagesFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getS3Image(String key) {
        try (S3ObjectInputStream inputStream = amazonS3.getObject(new GetObjectRequest(bucketName, key)).getObjectContent()) {
            //바이트로 변환
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ObjectMetadata createObjectMetadata(MultipartFile image) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(image.getContentType());
        objectMetadata.setContentLength(image.getInputStream().available());

        return objectMetadata;
    }
}
