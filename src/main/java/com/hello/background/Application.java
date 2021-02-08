package com.hello.background;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author wuketao
 * @date 2019/11/24
 * @Description
 */
@EnableSwagger2
@SpringBootApplication
//@EnableRedisHttpSession
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
    }
}
