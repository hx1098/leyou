package com.leyou.gateway.filters;

import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.common.utils.CookieUtils;
import com.leyou.gateway.config.FilterProperties;
import com.leyou.gateway.config.JwtProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author hx   <br>
 * @Title: <br>
 * @Package <br>
 * @Description: 拦截登录<br>
 * @date 2020/2/1913:25
 */
@Component
@EnableConfigurationProperties({JwtProperties.class, FilterProperties.class})
public class AuthFilter extends ZuulFilter {

    @Autowired
    private JwtProperties prop;

    @Autowired
    private FilterProperties filterProperties;

    /**
     * filter类型
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;  //前置过滤
    }

    /**
     * filter执行顺序，值越小优先级越高
     * 官方推荐使用x-1方式优先排序
     * @return
     */
    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
    }

    /**
     * filter 开启关闭
     * @return
     */
    @Override
    public boolean shouldFilter() {
       /* return true;*/
       //获取上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        //获取request
        HttpServletRequest request = ctx.getRequest();
        //获取请求的url路径
        String path = request.getRequestURI();
        //判断是否是白名单进行放行 放行返回false
        return !isAllowPath(path);//是否过滤
    }

    private boolean isAllowPath(String path) {
        //遍历白名单
        for (String allowPath : filterProperties.getAllowPaths()) {
            //判断是否允许
            if(path.startsWith(allowPath)){
                return true;
            }
        }
        return  false;
    }

    /**
     * 实现filter逻辑
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        //获取上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        //获取request
        HttpServletRequest request = ctx.getRequest();
        //获取cookie总的token，
//        Cookie[] cookies = request.getCookies();
        //获取token
        String token = CookieUtils.getCookieValue(request, prop.getCookieName());
        //解析token
        try {
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, prop.getPublicKey());
            //Todo 校验权限
            //校验用户的权限
        }catch (Exception e){
            //解析失败，未登录,进行拦截
            ctx.setSendZuulResponse(false);
            //返回状态码
            ctx.setResponseStatusCode(403);
        }

        return null;
    }
}
