package com.hello.background.service;

import com.google.common.base.Strings;
import com.hello.background.constant.CompanyEnum;
import com.hello.background.constant.JobTypeEnum;
import com.hello.background.domain.User;
import com.hello.background.repository.UserRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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

    /**
     * 通过id查询用户信息
     *
     * @param id
     * @return
     */
    public UserVO findById(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            return userVO;
        } else {
            return null;
        }
    }

    /**
     * 保存用户基本信息
     *
     * @param vo
     * @return
     */
    public UserVO saveBaseInfo(UserVO vo) {
        User user = new User();
        if (null == vo.getId()) {
            // 没有id表示新建用户。
            // 设置默认密码是1
            user.setPassword("1");
            user.setCreateDate(new Date());
        } else {
            // 有id表示用户已存在，此时需要从系统中查询
            user = userRepository.findById(vo.getId()).get();
        }
        // 更新基本信息字段
        user.setUsername(vo.getUsername());
        user.setRealname(vo.getRealname());
        user.setCompany(vo.getCompany());
        user.setJobType(vo.getJobType());
        if (vo.getJobType() == JobTypeEnum.CONSULTANT) {
            // 外包同事要保存客户公司
            user.setClientCompanyId(vo.getClientCompanyId());
        } else {
            // 非外包同事，取消客户公司信息
            user.setClientCompanyId(null);
        }
        user.setSalarybase(vo.getSalarybase());
        user.setCoverbase(vo.getCoverbase());
        user.setEnabled(vo.getEnabled());
        user.setOnBoardDate(vo.getOnBoardDate());
        user.setDimissionDate(vo.getDimissionDate());
        user.setRoles(vo.getRoles());
        user.setCheckKPI(vo.getCheckKPI());
        user.setRemainHolidayThing(vo.getRemainHolidayThing());
        user.setRemainHolidayIll(vo.getRemainHolidayIll());
        user.setTeamLeaderUserName(vo.getTeamLeaderUserName());
        // 更新数据库
        User returnUser = userRepository.save(user);
        UserVO returnUserVO = new UserVO();
        BeanUtils.copyProperties(returnUser, returnUserVO);
        return returnUserVO;
    }

    /**
     * 保存用户扩展信息
     *
     * @param vo
     * @return
     */
    public UserVO saveExtInfo(UserVO vo, Integer id) {
        User user = userRepository.findById(id).get();
        // 更新用户扩展信息字段
        user.setGender(vo.getGender());
        user.setPhoneNo(vo.getPhoneNo());
        user.setPhoneNo2(vo.getPhoneNo2());
        user.setPhoneNo3(vo.getPhoneNo3());
        user.setEmail(vo.getEmail());
        user.setWorkAddress(vo.getWorkAddress());
        user.setLifeAddress(vo.getLifeAddress());
        user.setHomeAddress(vo.getHomeAddress());
        user.setBank(vo.getBank());
        user.setCardBankName(vo.getCardBankName());
        user.setCardNumber(vo.getCardNumber());
        user.setGongJiJinAccount(vo.getGongJiJinAccount());
        user.setIdCardNo(vo.getIdCardNo());
        user.setBirthday(vo.getBirthday());
        user.setEducationBackground(vo.getEducationBackground());
        user.setEmergencyContact(vo.getEmergencyContact());
        user.setEmergencyTelephoneNo(vo.getEmergencyTelephoneNo());
        // 更新数据库
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
        return userList.stream().sorted(Comparator.comparing(User::getUsername)).map(user -> TransferUtil.transferTo(user, UserVO.class)).collect(Collectors.toList());
    }

    /**
     * 获取所有正常状态的全职员工
     *
     * @return
     */
    public List<UserVO> findAllEnabledFullTime() {
        List<User> userList = userRepository.findByEnabled(true);
        return userList.stream().filter(x -> JobTypeEnum.FULLTIME.equals(x.getJobType())).sorted(Comparator.comparing(User::getUsername)).map(user -> TransferUtil.transferTo(user, UserVO.class)).collect(Collectors.toList());
    }

    /**
     * 获取所有正常状态的全职员工和实习生
     *
     * @return
     */
    public List<UserVO> findAllEnabledFullTimeAndIntern() {
        List<User> userList = userRepository.findByEnabled(true);
        return userList.stream().filter(x -> (JobTypeEnum.FULLTIME.equals(x.getJobType()) || JobTypeEnum.INTERN.equals(x.getJobType()))).sorted(Comparator.comparing(User::getUsername)).map(user -> TransferUtil.transferTo(user, UserVO.class)).collect(Collectors.toList());
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
        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order(Sort.Direction.DESC, "enabled"));
        orderList.add(new Order(Sort.Direction.ASC, "id"));
        Pageable pageable = new PageRequest(currentPage - 1, pageSize, new Sort(orderList));
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
            if (userVO.getEnabled()) {
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
     * 查询正常状态的用户
     *
     * @param search 搜索关键字
     * @return
     */
    public List<UserVO> queryEnabled(String search) {
        return query(search).stream().filter(u -> u.getEnabled()).collect(Collectors.toList());
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

    /**
     * 通过Scope查询用户
     *
     * @param scope
     * @param userVO
     * @return
     */
    public List<UserVO> findByScope(String scope, UserVO userVO) {
        List<User> userList = new ArrayList<>();
        if ("myself".equals(scope)) {
            userList.add(TransferUtil.transferTo(userVO, User.class));
        } else if ("all".equals(scope)) {
            // 查看所有在职的全职人员pipeline情况
            userList = userRepository.findAll();
        } else if ("shanghai".equals(scope)) {
            // 查看上海所有在职的全职人员pipeline情况
            userList = userRepository.findAll().stream().filter(u -> null != u.getCompany() && u.getCompany().equals(CompanyEnum.Shanghaihailuorencaifuwu)).filter(y -> !("Victor".equals(y.getUsername()) || "Ellen".equals(y.getUsername()))).collect(Collectors.toList());
        } else if ("shenyang".equals(scope)) {
            // 查看沈阳所有在职的全职人员pipeline情况
            userList = userRepository.findAll().stream().filter(u -> null != u.getCompany() && u.getCompany().equals(CompanyEnum.Shenyanghailuorencaifuwu)).collect(Collectors.toList());
        } else if ("wuhan".equals(scope)) {
            // 查看武汉所有在职的全职人员pipeline情况
            userList = userRepository.findAll().stream().filter(u -> null != u.getCompany() && u.getCompany().equals(CompanyEnum.Wuhanhailuorencaifuwu)).collect(Collectors.toList());
        } else if ("beijing".equals(scope)) {
            // 查看北京所有在职的全职人员pipeline情况
            userList.add(userRepository.findByUsername("Victor"));
            userList.add(userRepository.findByUsername("Ellen"));
        }
        return userList.stream().filter(u -> u.getEnabled() && null == u.getDimissionDate()).map(u -> TransferUtil.transferTo(u, UserVO.class)).collect(Collectors.toList());
    }
}
