package com.leyou.upload.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyExcetion;
import com.leyou.upload.config.UploadProperties;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.assertj.core.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2019/12/12
 * Time:15:07
 */
@EnableConfigurationProperties(UploadProperties.class) //直接注入
@Service
@Slf4j
public class UploadService {

    @Autowired
    private  UploadProperties uploadProperties;

   // private  static  final List<String> ALLOW_TYPES = Arrays.asList("image/jpeg","image/png","image/bmp");

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    public String uploadImage(MultipartFile file){
        try {
            //校验文件类型
            String contentType = file.getContentType();
//            System.err.println(uploadProperties.getAllowTypes());
//            System.err.println(contentType);
            if (!uploadProperties.getAllowTypes().contains(contentType)){
                throw  new LyExcetion(ExceptionEnum.FILE_TYPE_NOT_ERROR);
            }
            //校验文件内容
            BufferedImage read = ImageIO.read(file.getInputStream());
            if(read == null){
                throw  new LyExcetion(ExceptionEnum.FILE_TYPE_NOT_ERROR);
            }


           /* File dest = new File("E:\\ziji\\leyou2\\upload", file.getOriginalFilename());
            //保存文件到本地
            //返回路径
            file.transferTo(dest);*/
            //上传到fadfs
            String extension = StringUtils.substringAfterLast(file.getOriginalFilename(),".");//获取上传文件的后缀名字
            StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);


            System.err.println(uploadProperties.getBaseUrl());
            System.err.println(storePath.getFullPath());
            return uploadProperties.getBaseUrl()  + storePath.getFullPath();
        } catch (Exception e) {
           log.error("[文件上传] 文件上传失败！",e);
           new LyExcetion(ExceptionEnum.UPLOAD_ERROR);
           return  null;
        }

    }
}
