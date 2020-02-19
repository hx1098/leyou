package com.leyou.user.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyExcetion;
import com.leyou.common.utils.NumberUtils;
import com.leyou.user.mapper.UserMapper;
import com.leyou.user.pojo.User;
import com.leyou.user.utils.CodecUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author hx   <br>
 * @Title: <br>
 * @Package <br>
 * @Description: UserService<br>
 * @date 2020/2/1611:08
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;
    //redis中存储用户验证的验证码
    public static final String KEY_PRIFIX = "user:verify:phone";
    /**
     * 校验数据，用户手机号和用户名校验
     * @param data 要校验的数据
     * @param type 1:用户名  2：手机 3.邮箱
     * @return
     */
    public Boolean checkData(String data, Integer type) {
        User user = new User();
        //判断数据类型
        switch (type){
            case 1:
                user.setUsername(data);
                break;
            case 2:
                user.setPhone(data);
                break;
            default:
                throw new LyExcetion(ExceptionEnum.INVALID_USER_DATA_TYPE);
        }
        return userMapper.selectCount(user) == 0;
    }

    public void sentCode(String phone){
        //生成key
        String key = KEY_PRIFIX + phone;
        //生成验证码
        String code = NumberUtils.generateCode(6);//生成随即六位数

        HashMap<String, String> msg = new HashMap<>();
        msg.put("phone", phone);
        msg.put("code", code);
        //交换机，key
        amqpTemplate.convertAndSend("ly.sms.exchange", "sms.verify.code", msg);
        //保存验证码到redis,设置时长为5分钟
        redisTemplate.opsForValue().set(key,code,5, TimeUnit.MINUTES);


    }

    public void register(@Valid User user, String code) {
        //region 注册基本逻辑
        //1）校验短信验证码,若验证码不一致，直接返回
        //2.生成盐
        //3.对密码加密
        //4.写入数据库
        //5.删除Redis中的验证码
        //endregion
      /*  String key = KEY_PRIFIX + user.getPhone();//获取存在redis中的验证码的key
        String codeCache = redisTemplate.opsForValue().get(key);
//        if(!codeCache.equals(code)){//检查验证码是否正确
//            return  false;
//        }
        user.setId(null);
        user.setCreated(new Date());

        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);
        user.setPassword(CodecUtils.md5Hex(user.getPassword(),user.getSalt()));

        //写入数据库
        Boolean bool = userMapper.insert(user) == 1;
        //注册成功，删掉其中的验证码
        if (bool){
            try {
                redisTemplate.delete(key);
            }catch (Exception e){
               log.error("删除缓存验证码失败，code：{}", code, e);
            }
        }
       return bool;*/
        // 校验验证码
      /*  String cacheCode = redisTemplate.opsForValue().get(KEY_PRIFIX + user.getPhone());
        if(!StringUtils.equals(cacheCode, code)){
            throw new LyExcetion(ExceptionEnum.INVALID_VERIFY_CODE);
        }*/

        // 对密码进行加密
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);
        user.setPassword(CodecUtils.md5Hex(user.getPassword() , salt));

        // 写入数据库
        user.setCreated(new Date());
        userMapper.insert(user);

    }

    public User queryUser(String userName, String password) {
        User user = new User();
//        user.setPassword(password);
        user.setUsername(userName);
        //查询用户名是否存在
        User user1 = userMapper.selectOne(user);
        if(user1 == null){
            return null;
        }
        //校验用户密码
        if(!user1.getPassword().equals((CodecUtils.md5Hex(password,user1.getSalt())))){
           return  null;
        }
        return user1;
    }
}
