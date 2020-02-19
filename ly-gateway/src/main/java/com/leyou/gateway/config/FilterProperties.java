package com.leyou.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author hx   <br>
 * @Title: <br>
 * @Package <br>
 * @Description: 网关白名单<br>
 * @date 2020/2/1914:03
 */
@Data
@ConfigurationProperties(prefix = "leyou.filter")
public class FilterProperties {

    private List<String> allowPaths;



}
