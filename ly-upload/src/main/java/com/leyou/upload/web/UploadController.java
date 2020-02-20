package com.leyou.upload.web;

import com.leyou.upload.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.Response;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2019/12/12
 * Time:15:04
 */
@RestController
@RequestMapping("upload")
public class UploadController {

     @Autowired
     private UploadService uploadService;

     @PostMapping("image")
     public ResponseEntity<String> uploadImage(@RequestParam("file")MultipartFile file){
         String url = uploadService.uploadImage(file);
         return ResponseEntity.ok(url);
     }

}
