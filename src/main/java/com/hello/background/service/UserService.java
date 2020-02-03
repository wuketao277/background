package com.hello.background.service;

import com.hello.background.domain.User;
import com.hello.background.repository.UserRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuketao
 * @date 2020/2/3
 * @Description
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 通过使能状态查找用户
     *
     * @param enabled
     * @return
     */
    public List<UserVO> findByEnabled(boolean enabled) {
        List<User> userList = userRepository.findByEnabled(enabled);
        return userList.stream().map(user -> TransferUtil.transferTo(user, UserVO.class)).collect(Collectors.toList());
    }
}
