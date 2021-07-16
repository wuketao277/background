package com.hello.background.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * @author wuketao
 * @date 2021/7/14
 * @Description
 */
@Configuration
public class CommonConfig {
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个文件大小
        factory.setMaxFileSize("1000MB"); // KB,MB
        /// 总上传文件大小
        factory.setMaxRequestSize("1000MB");
        return factory.createMultipartConfig();
    }
}
