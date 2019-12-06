package com.hello.background.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理器
 *
 * @author wuketao
 * @date 2019/10/23
 * @Description
 */
@Slf4j
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    /**
     * 认证失败处理方法
     *
     * @param req
     * @param res
     * @param auth
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse res, AuthenticationException auth)
            throws IOException, ServletException {
        log.info("用户认证失败");
        // 认证失败跳转到登录页面
        res.sendRedirect("/login");
    }
}
