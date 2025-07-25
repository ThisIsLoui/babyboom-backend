package com.babyboombackend.service;

import com.babyboombackend.exception.BaseException;
import com.babyboombackend.properties.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@Slf4j
public class MinioService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioProperties minioProperties;


    public String uploadFile(MultipartFile file) {
        try {
            String bucket = minioProperties.getBucket();

            // 检查桶是否存在，不存在就创建
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }

            // 设置文件名（可加UUID避免冲突）
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename()  ;

            // 上传文件
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(filename)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            // 返回访问链接（可换成预签名 URL）
            return minioProperties.getEndpoint() + "/" + bucket + "/" + filename;

        } catch (Exception e) {
            log.error("上传文件到MinIO失败", e);
            throw new BaseException("上传失败：" + e.getMessage());
        }
    }
}
