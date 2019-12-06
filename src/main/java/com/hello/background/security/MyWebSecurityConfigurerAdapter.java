package com.hello.background.security;

import com.hello.background.security.handler.MyAuthenticationFailureHandler;
import com.hello.background.security.handler.MyAuthenticationSuccessHandler;
import com.hello.background.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Component;

/**
 * 自定义web安全配置类
 *
 * @author wuketao
 * @date 2019/10/23
 * @Description
 */
@Configuration
@Component
@EnableWebSecurity
public class MyWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    /**
     * 注入登录成功处理器
     */
    @Autowired
    private MyAuthenticationSuccessHandler successHandler;
    /**
     * 注入登录失败处理器
     */
    @Autowired
    private MyAuthenticationFailureHandler failureHandler;
    /**
     * 注入自定义用户授权信息获取类
     */
    @Autowired
    private MyUserDetailService myUserDetailService;

    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    /**
     * 用户认证信息配置
     * 通过自定义类控制用户认证方式
     * 自定义加密方法
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService).passwordEncoder(new PasswordEncoder() {
            /**
             * 对表单密码进行加密
             */
            @Override
            public String encode(CharSequence rawPassword) {
                // 调用自定义MD5工具类对用户输入的密码进行加密
                return MD5Util.encode((String) rawPassword);
            }

            /**
             * 加密的密码与数据库密码进行比对
             * rawPassword 表单密码     encodedPassword数据库密码
             */
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                // 返回true代表认证成功，false失败
                return MD5Util.encode((String) rawPassword).equals(encodedPassword);
            }
        });
    }

    /**
     * httpSecurity拦截资源配置
     * 获得所有资源数据。每个资源配置有若干个可以访问该资源的角色。
     * 对于没有配置角色的资源，表示该资源不能访问。
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizeRequests = http.authorizeRequests();
        // 定义登录页信息
        authorizeRequests
                .antMatchers("/login").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                // 如果希望登录后固定跳转到一个位置，可以配置successHandler；
                // 如果希望登录后自动放回到原先访问的位置，则不要配置successHandler
                //.successHandler(successHandler)
                .failureHandler(failureHandler)
                .and().csrf().disable();

        // 添加自定安全终端过滤器，从而注入自定义准入决策管理器和自定义调用安全过滤器
        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
    }
}
