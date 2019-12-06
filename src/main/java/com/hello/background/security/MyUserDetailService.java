package com.hello.background.security;

import com.hello.background.domain.User;
import com.hello.background.domain.UserRole;
import com.hello.background.repository.UserRepository;
import com.hello.background.repository.UserRoleRepository;
import com.hello.background.utils.TransferUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 自定义UserDetailsService类
 *
 * @author wuketao
 * @date 2019/10/23
 * @Description
 */
@Service
public class MyUserDetailService implements UserDetailsService {
    /**
     * 注入用户映射工具类
     */
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    /**
     * 重写方法，通过登录名获取用户信息
     *
     * @param username 登录名
     * @return 用户信息
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 通过登录名获取用户信息
        User user = userRepository.findByUsername(username);
        SecurityUser securityUser = new SecurityUser();
        TransferUtil.transfer(user, securityUser);
        if (!Objects.isNull(user)) {
            // 查询用户授权资源标识
            List<UserRole> roleList = userRoleRepository.findByUserName(username);
            if (roleList != null) {
                List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                roleList.stream().forEach(ur -> {
                    authorities.add(new SimpleGrantedAuthority(ur.getRoleName()));
                });
                // 添加到用户授权集合中
                securityUser.setAuthorities(authorities);
            }
        }
        return securityUser;
    }
}