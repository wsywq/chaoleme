package com.ywq.controller;

import com.ywq.common.ResponseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Value("${sftp.path}")
    private String basePath;

    @PostMapping("/upload")
    public ResponseTemplate<String> uploadFile(@RequestParam("image") MultipartFile file) {
        log.info("Upload file");
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
        File fileDir = new File(basePath);
        log.info("上传文件夹路径" + fileDir.getAbsolutePath());
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        try {
            File result = new File(basePath + File.separator +fileName);
            log.info("目标文件绝对路径" + result.getAbsolutePath());
            file.transferTo(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseTemplate.success(fileName);
    }

    @GetMapping("/download")
    public void downloadFile(String name, HttpServletResponse response) {
        try {
            FileInputStream fileInputStream = new FileInputStream(basePath + name);
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != 0) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/hello")
    public ResponseTemplate<String> helloNewFood() {
        return ResponseTemplate.success("OK");
    }

}
