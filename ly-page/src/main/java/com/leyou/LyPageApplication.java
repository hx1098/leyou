package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author hx   <br>
 * @Title: <br>
 * @Package <br>
 * @Description: <br>
 * @date 2020/2/1220:12
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LyPageApplication {
    public static void main(String[] args) {
        SpringApplication.run(LyPageApplication.class, args);
    }
}
