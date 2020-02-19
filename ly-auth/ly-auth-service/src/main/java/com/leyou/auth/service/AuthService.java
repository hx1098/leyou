package com.leyou.auth.service;

import com.leyou.auth.client.UserClient;
import com.leyou.auth.config.JwtProperties;
import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hx   <br>
 * @Title: <br>
 * @Package <br>
 * @Description: <br>
 * @date 2020/2/1813:19
 */
@Service
public class AuthService {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private UserClient userClient;


    public String login(String userName, String password) {
        //远程调用查询用户的接口
        User user = this.userClient.queryUser(userName, password);
        //为空的话直接返回
        if(user==null){
            return null;
        }

        //初始化载荷
        UserInfo userInfo = new UserInfo(user.getId(),user.getUsername());
        //生成jwt类型的token
        try {
           return JwtUtils.generateToken(userInfo,jwtProperties.getPrivateKey(),jwtProperties.getExpire());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
}
