package com.leyou.upload.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2019/12/13
 * Time:14:00
 * 文件属性
 */
@Data
@ConfigurationProperties(prefix = "ly.upload")
public class UploadProperties {
    /**图片地址*/
    private  String baseUrl;

    /***/
    private List<String> allowTypes;

}
