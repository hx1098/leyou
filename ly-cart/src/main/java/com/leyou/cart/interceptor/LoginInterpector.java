package com.leyou.cart.interceptor;

import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.cart.config.JwtProperties;
import com.leyou.common.utils.CookieUtils;
import com.leyou.user.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hx   <br>
 * @Title: <br>
 * @Package <br>
 * @Description: 登录拦截器 <br>
 * @date 2020/2/2015:37
 */
@Component
@EnableConfigurationProperties(JwtProperties.class)
public class LoginInterpector extends HandlerInterceptorAdapter {

    @Autowired
    private JwtProperties prop;

    /**
     * 定义一个线程域，用于存放登录的用户
     */
    @Autowired
    private static final ThreadLocal<UserInfo> THREAD_LOCAL = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取token
        String token = CookieUtils.getCookieValue(request, prop.getCookieName());
        //判断是否为空
        if (StringUtils.isBlank(token)){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());//未登录返回401
            return false;
        }
        //不为空，解析token，获取用户信息
        try {
            //解析成功，说明已经登录
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, prop.getPublicKey());
            //放入线程域
            THREAD_LOCAL.set(userInfo);
            return  true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    //将此方法返回出去
    public static UserInfo get(){
        return THREAD_LOCAL.get();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //释放资源，必须操作，使用的是线程池，一次请求执行完之后，线程并没有结束
       THREAD_LOCAL.remove();
    }
}
