package com.hello.background.service;

import com.hello.background.constant.RoleEnum;
import com.hello.background.domain.SalarySpecialItem;
import com.hello.background.repository.SalarySpecialItemRepository;
import com.hello.background.repository.UserRoleRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.SalarySpecialItemVO;
import com.hello.background.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

/**
 * 工资特殊项服务
 *
 * @author wuketao
 * @date 2020/2/3
 * @Description
 */
@Transactional
@Service
public class SalarySpecialItemService {

    @Autowired
    private SalarySpecialItemRepository salarySpecialItemRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    public SalarySpecialItemVO save(SalarySpecialItemVO vo) {
        SalarySpecialItem salarySpecialItem = new SalarySpecialItem();
        BeanUtils.copyProperties(vo, salarySpecialItem);
        salarySpecialItem = salarySpecialItemRepository.save(salarySpecialItem);
        vo.setId(salarySpecialItem.getId());
        return vo;
    }


    /**
     * 查询分页
     *
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    public Page<SalarySpecialItemVO> queryPage(HttpSession session, String search, Integer currentPage, Integer pageSize) {
        UserVO user = (UserVO) session.getAttribute("user");
        Pageable pageable = new PageRequest(currentPage - 1, pageSize);
        Page<SalarySpecialItem> salarySpecialItemPage = null;
        long total = 0;
        if (user.getRoles().contains(RoleEnum.ADMIN)) {
            // 如果是管理员，可以查看任意数据
            search = "%" + search + "%";
            salarySpecialItemPage = salarySpecialItemRepository.findByConsultantRealNameLikeOrConsultantUserNameLikeOrMonthLikeOrDescriptionLikeOrderByUpdateTimeDesc(search, search, search, search, pageable);
            total = salarySpecialItemRepository.countByConsultantRealNameLikeOrConsultantUserNameLikeOrMonthLikeOrDescriptionLike(search, search, search, search);
        } else {
            salarySpecialItemPage = salarySpecialItemRepository.findByConsultantUserNameOrderByMonthDesc(user.getUsername(), pageable);
            total = salarySpecialItemRepository.countByConsultantUserName(user.getUsername());
        }
        Page<SalarySpecialItemVO> map = salarySpecialItemPage.map(x -> TransferUtil.transferTo(x, SalarySpecialItemVO.class));
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()),
                total);
        return map;
    }

    /**
     * 通过主键删除
     *
     * @param id
     */
    public void deleteById(Integer id) {
        salarySpecialItemRepository.deleteById(id);
    }
}
