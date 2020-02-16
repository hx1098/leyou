package com.leyou.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author hx   <br>
 * @Title: <br>
 * @Package <br>
 * @Description: 短信发送配置类 <br>
 * @date 2020/2/1515:14
 */
@Data
@ConfigurationProperties(prefix = "ly.sms")
public class SmsProperties {

    String accessKeyId;

    String accessKeySecret;

    String signName;

    String verifyCodeTemplate;
}
