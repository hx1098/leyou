package com.leyou.cart.config;

import com.leyou.cart.interceptor.LoginInterpector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author hx   <br>
 * @Title: <br>
 * @Package <br>
 * @Description: 拦截器注册器配置<br>
 * @date 2020/2/2016:02
 */
@Configuration
public class LeyouConfiguration implements WebMvcConfigurer {

    @Autowired
    private LoginInterpector loginInterpector;

    /**
     * 拦截器注册器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // /* 拦截所有的以及路径
        // /** 拦截所有的路径包含二级三级路径
        registry.addInterceptor(this.loginInterpector).addPathPatterns("/**");
    }
}
