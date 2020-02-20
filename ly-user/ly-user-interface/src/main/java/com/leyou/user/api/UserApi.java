package com.leyou.user.api;

import com.leyou.user.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author hx   <br>
 * @Title: <br>
 * @Package <br>
 * @Description:  给远程调用<br>
 * @date 2020/2/1813:23
 */
public interface UserApi {
    /**
     * 根据用户名和密码查询用户
     * @param userName
     * @param password
     * @return
     */
    @GetMapping("query")
    User queryUser(@RequestParam("userName") String userName, @RequestParam("password") String password);

}
