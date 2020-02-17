package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author hx   <br>
 * @Title: <br>
 * @Package <br>
 * @Description: 用户注册<br>
 * @date 2020/2/1610:47
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.leyou.user.mapper")
public class LeyouUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeyouUserApplication.class, args);
    }
}
