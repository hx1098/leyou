package com.leyou.cart.service;

import com.leyou.auth.entity.UserInfo;
import com.leyou.cart.client.GoodsClient;
import com.leyou.cart.entity.Cart;
import com.leyou.cart.interceptor.LoginInterpector;
import com.leyou.common.utils.JsonUtils;
import com.leyouo.item.pojo.Sku;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hx   <br>
 * @Title: <br>
 * @Package <br>
 * @Description: 购物车处理类<br>
 * @date 2020/2/2114:29
 */
@Service
public class CartService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private GoodsClient goodsClient;

    /**
     * 增加购物车数据
     * @param cart
     */
    public void addCart(Cart cart) {
        //先查询用户是否有购物车
        UserInfo userInfo = LoginInterpector.get();
        BoundHashOperations<String, Object, Object> hashOperations = redisTemplate.boundHashOps(userInfo.getId().toString());
        //判断商品中时候有这个id
        Integer num = cart.getNum();//当前需要增加的商品数量
        String skuId = cart.getSkuId().toString();
        if (hashOperations.hasKey(skuId)) {//有，更新数量
            String cartJson = hashOperations.get(skuId).toString();
            cart = JsonUtils.parse(cartJson, Cart.class);
            cart.setNum(num + cart.getNum());
        } else {//没有这个商品的id，新增一个
            cart.setUserId(userInfo.getId());
            //查询商品信息
            Sku sku = this.goodsClient.querySkuById(cart.getSkuId());
            cart.setPrice(sku.getPrice());
            //设置图片的第一张
            cart.setImage(StringUtils.isBlank(sku.getImages()) ? "" : StringUtils.split(sku.getImages(), ",")[0]);
            cart.setOwnSpec(sku.getOwnSpec());
            cart.setTitle(sku.getTitle());
        }
        hashOperations.put(skuId, JsonUtils.serialize(cart));
        //没有的话新增一个购物车
    }

    /**
     * 查询购物车数据
     * @return
     */
    public List<Cart> queryCarts() {
        //获取用户信息
        UserInfo userInfo = LoginInterpector.get();
        //判断一下是否有改用户的购物车
        Boolean aBoolean = redisTemplate.hasKey(userInfo.getId().toString());
        //没有该用户
        if (!aBoolean) {
            return null;
        }
        //又改用户的购物车
        BoundHashOperations<String, Object, Object> hashOperations = redisTemplate.boundHashOps(userInfo.getId().toString());
        //拿到购物车的数据
        List<Object> cartJsons = hashOperations.values();

        return cartJsons.stream().map(cartJson -> JsonUtils.parse(cartJson.toString(), Cart.class)).collect(Collectors.toList());

    }

    /**
     * 根据skuID进行更新购物车数据
     * @param skuId
     * @param num
     */
    public void updateNum(Long skuId, Integer num) {
        //获取登录用户
        UserInfo userInfo = LoginInterpector.get();
        //获取购物车
        BoundHashOperations<String, Object, Object> hashOperations = redisTemplate.boundHashOps(userInfo.getId().toString());
        //根据SkuID获取购物车的Json
        String json = hashOperations.get(skuId.toString()).toString();
        //更新数据
        Cart cart = JsonUtils.parse(json, Cart.class);
        cart.setNum(num);
        //序列化数据后存入redis
        hashOperations.put(skuId.toString(),JsonUtils.serialize(cart));
    }

    /**
     * 根据skuId删除redis中的商品
     * @param skuId
     */
    public void deleteCart(String skuId) {
        //获取用户id，根据id才能删除购物车
        UserInfo userInfo = LoginInterpector.get();
        //获取用户的购物车List
        BoundHashOperations<String, Object, Object> hashOperations = redisTemplate.boundHashOps(userInfo.getId().toString());
        //根据skuId删除购物车的中的商品
        hashOperations.delete(skuId);

    }
}
