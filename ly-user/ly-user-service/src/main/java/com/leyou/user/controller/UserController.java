package com.leyou.user.controller;

import com.leyou.user.pojo.User;
import com.leyou.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

/**
 * @author hx   <br>
 * @Title: <br>
 * @Package <br>
 * @Description: <br>
 * @date 2020/2/1611:10
 */
@Api(value = "UserController|用户中心")
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 校验数据，用户手机号和用户名校验
     *
     * @param data 要校验的数据
     * @param type 1:用户名  2：手机 3.邮箱
     * @return
     */
    @ApiOperation(value = "校验数据，用户手机号和用户名校验")
    @ResponseBody
    @RequestMapping(value = "/check/{data}/{type}",method = RequestMethod.GET)
    public ResponseEntity<Boolean> checkData(@PathVariable("data") String data, @PathVariable("type") Integer type) {
        return ResponseEntity.ok(userService.checkData(data, type));
    }

    /**
     * 注册发送验证码功能
     *
     * @param phone 目标手机号
     * @return
     */
    @ApiOperation(value = "注册发送验证码功能")
    @PostMapping("send")
    public ResponseEntity<Void> sendCode(@RequestParam("phone") String phone) {
        userService.sentCode(phone);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 注册
     * @param user 用户信息
     * @param code 短信验证码
     * @return
     */
    @ApiOperation(value = "注册")
    @PostMapping("register")
    public ResponseEntity<Void> register(@RequestBody User user, BindingResult result, @RequestParam("code") String code) {
        if(result.hasErrors()){//BindingResult 如果有错抛出异常继续执行
            throw new RuntimeException(result.getFieldErrors()
                    .stream().map(e -> e.getDefaultMessage()).collect(Collectors.joining("|")));
        }
        userService.register(user, code);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据用户名和密码查询用户
     * @param userName
     * @param password
     * @return
     */
    @ApiOperation(value = "根据用户名和密码查询用户")
    @GetMapping("query")
    public ResponseEntity<User> queryUser(@RequestParam("userName") String userName,@RequestParam("password") String password){
      User user =  userService.queryUser(userName,password);
      return ResponseEntity.ok(user);
    }


}
