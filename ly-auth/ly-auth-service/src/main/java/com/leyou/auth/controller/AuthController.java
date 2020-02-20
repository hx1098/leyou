package com.leyou.auth.controller;

import com.leyou.auth.config.JwtProperties;
import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.service.AuthService;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyExcetion;
import com.leyou.common.utils.CookieUtils;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.table.TableRowSorter;

/**
 * @author hx   <br>
 * @Title: <br>
 * @Package <br>
 * @Description: 授权中心<br>
 * @date 2020/2/1813:17
 */
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtProperties jwtProperties;


    @PostMapping("login")
    public ResponseEntity<Void> login(@RequestParam String username, @RequestParam String password,
                                      HttpServletRequest req, HttpServletResponse res) {
        //调 用service方法生层jwt
        String token = this.authService.login(username, password);
        if (StringUtils.isBlank(token)) {
            return ResponseEntity.badRequest().build();
        }
        //使用CookiesUtils.setCookie（）方法 吧token设置cookie
        // CookieUtils.setCookie(req, res, jwtProperties.getCookieName(), token, jwtProperties.getExpire() * 60, null, true);
        CookieUtils.newBuilder(res).httpOnly().request(req).maxAge(jwtProperties.getExpire() * 60).build(jwtProperties.getCookieName(),token);

        return ResponseEntity.ok().build();
    }

    /**
     * 校验用户登录状态
     * @param token
     * @return
     */
    @GetMapping("verify")
    public ResponseEntity<UserInfo> verify(@CookieValue("LY_TOKEN")String token, HttpServletRequest req, HttpServletResponse res){
        //没有token是没有登录的
        if (StringUtils.isBlank(token)){
            throw new LyExcetion(ExceptionEnum.UN_AUTHORIZED);
        }
        //解析token
        try{
            UserInfo infoFromToken = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());

            //刷新token，重新生成token
            String newToken = JwtUtils.generateToken(infoFromToken, jwtProperties.getPrivateKey(), jwtProperties.getExpire() * 60);
            CookieUtils.newBuilder(res).httpOnly().request(req).maxAge(jwtProperties.getExpire() * 60).build(jwtProperties.getCookieName(),newToken);


            //已登录返回用户信息
            return ResponseEntity.ok(infoFromToken);
        }catch (Exception e){
            //token过期或者token被篡改
            throw new LyExcetion(ExceptionEnum.UN_AUTHORIZED);
        }


    }

}
