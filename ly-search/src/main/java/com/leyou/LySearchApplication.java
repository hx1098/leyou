package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2020/2/9
 * Time:14:03
 * 搜索服务
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LySearchApplication {
    public static void main(String[] args) {
         SpringApplication.run(LySearchApplication.class);
    }
}
