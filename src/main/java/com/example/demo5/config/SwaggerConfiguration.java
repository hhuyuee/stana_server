package com.example.demo5.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/***
 * description
 *
 * @author huyue87@jd.com
 * @since 2.0.0
 * @date 2020/11/26 14:15
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    // 创建api连接
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
    }

    //基本信息的配置，信息会在api文档上显示
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("驻留点分析平台测试的接口文档")
                .description("驻留点分析平台相关接口的文档")
                .termsOfServiceUrl("http://localhost:8080")
                .version("1.0")
                .build();
    }
}
