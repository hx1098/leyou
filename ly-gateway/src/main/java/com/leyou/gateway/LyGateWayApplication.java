package com.leyou.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2019/12/9
 * Time:21:44
 */
@EnableZuulProxy
@SpringCloudApplication //代表springbootapplication  熔断器
public class LyGateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(LyGateWayApplication.class,args);
    }
}
