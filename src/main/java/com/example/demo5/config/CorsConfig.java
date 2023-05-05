package com.example.demo5.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: hujian9@jd.com
 * @description: 跨域配置
 * @date: Created in 2019-09-16 15:52
 * @modified by:
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * 注册一个跨域参数映射配置
     *
     * @param registry 跨域注册器
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域的路径
        registry.addMapping("/**")
                //设置允许跨域请求的域名
                .allowedOrigins("*")
                //是否允许证书,不再默认开启
                .allowCredentials(true)
                //设置允许的方法
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                //跨域允许时间
                .maxAge(3600);
    }
}
