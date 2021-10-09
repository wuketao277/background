package com.hello.background.service;

import com.google.common.base.Strings;
import com.hello.background.domain.User;
import com.hello.background.repository.UserRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author wuketao
 * @date 2020/2/3
 * @Description
 */
@Transactional
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserVO save(UserVO vo) {
        if (null == vo.getId()) {
            // 没有id表示新建用户。
            // 设置默认密码是1
            vo.setPassword("1");
        }
        User user = new User();
        BeanUtils.copyProperties(vo, user);
        User returnUser = userRepository.save(user);
        UserVO returnUserVO = new UserVO();
        BeanUtils.copyProperties(returnUser, returnUserVO);
        return returnUserVO;
    }

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


    /**
     * 查询分页
     *
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    public Page<UserVO> queryPage(String search, Integer currentPage, Integer pageSize) {
        Pageable pageable = new PageRequest(currentPage - 1, pageSize);
        Page<User> page = null;
        long total = 0;
        if (Strings.isNullOrEmpty(search)) {
            page = userRepository.findAll(pageable);
            total = userRepository.count();
        } else {
            page = userRepository.findByRealnameLikeOrUsernameLike(search, search, pageable);
            total = userRepository.countByRealnameLikeOrUsernameLike(search, search);
        }
        Page<UserVO> map = page.map(x -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(x, userVO);
            if (userVO.isEnabled()) {
                userVO.setEnabledName("正常");
            } else {
                userVO.setEnabledName("停用");
            }
            return userVO;
        });
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()),
                total);
        return map;
    }


    /**
     * 查询
     *
     * @param search 搜索关键字
     * @return
     */
    public List<UserVO> query(String search) {
        List<User> userList = userRepository.findByRealnameLikeOrUsernameLike(search, search);
        List<UserVO> userVOList = new ArrayList<>();
        return userList.stream().map(pojo -> {
            UserVO vo = new UserVO();
            BeanUtils.copyProperties(pojo, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 更新密码
     *
     * @param oldPassword
     * @param newPassword
     * @param userId
     */
    public boolean updatePassword(String oldPassword, String newPassword, Integer userId) {
        boolean result = false;
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (oldPassword.equals(user.getPassword())) {
                user.setPassword(newPassword);
                userRepository.save(user);
                result = true;
            }
        }
        return result;
    }
}
