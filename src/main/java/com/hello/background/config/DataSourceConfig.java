package com.hello.background.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuketao
 * @date 2019/11/24
 * @Description
 */
@Configuration
public class DataSourceConfig {
    /**
     * 配置Druid数据源，写数据库
     *
     * @return
     */
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DruidDataSource writeDataSource() {
        return new DruidDataSource();
    }
}
