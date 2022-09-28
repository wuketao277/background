package com.hello.background.service;

import com.hello.background.common.CommonUtils;
import com.hello.background.domain.Holiday;
import com.hello.background.repository.HolidayRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.HolidayVO;
import com.hello.background.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 假期服务
 *
 * @author wuketao
 * @date 2019/12/14
 * @Description
 */
@Transactional
@Slf4j
@Service
public class HolidayService {

    @Autowired
    private HolidayRepository holidayRepository;

    /**
     * 通过id，查询假期信息
     *
     * @param id
     * @return
     */
    public HolidayVO findById(Integer id) {
        Optional<Holiday> holidayOptional = holidayRepository.findById(id);
        if (holidayOptional.isPresent()) {
            Holiday holiday = holidayOptional.get();
            return TransferUtil.transferTo(holiday, HolidayVO.class);
        }
        return null;
    }

    /**
     * 保存假期
     *
     * @param vo
     * @return
     */
    public HolidayVO save(HolidayVO vo, UserVO user) {
        if (null == vo.getUserId()) {
            // 新增时，当前用户就是申请人
            vo.setUserId(user.getId());
            vo.setUserName(user.getUsername());
            vo.setUserRealName(user.getRealname());
        }
        vo.setApproveUserId(user.getId());
        vo.setApproveUserName(user.getUsername());
        vo.setApproveUserRealName(user.getRealname());
        Holiday holiday = TransferUtil.transferTo(vo, Holiday.class);
        holiday = holidayRepository.save(holiday);
        return TransferUtil.transferTo(holiday, HolidayVO.class);
    }


    /**
     * 通过id
     *
     * @param id
     */
    public void deleteById(Integer id) {
        holidayRepository.deleteById(id);
    }

    /**
     * 查询分页
     *
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    public Page<HolidayVO> queryPage(String search, Integer currentPage, Integer pageSize, UserVO userVO) {
        Pageable pageable = new PageRequest(currentPage - 1, pageSize, Sort.Direction.DESC, "id");
        List<String> searchWordList = CommonUtils.splitSearchWord(search);
        Specification<Holiday> specification = new Specification<Holiday>() {
            @Override
            public Predicate toPredicate(Root<Holiday> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                for (String searchWord : searchWordList) {
                    list.add(criteriaBuilder.and(criteriaBuilder.or(
                            getPredicate("userName", searchWord, root, criteriaBuilder)
                            , getPredicate("userRealName", searchWord, root, criteriaBuilder)
                            , getPredicate("holidayDate", searchWord, root, criteriaBuilder)
                            , getPredicate("remark", searchWord, root, criteriaBuilder)
                    )));
                }
                // 如果不是管理员，只能查看自己的假期
                if (!userVO.getRoleList().contains("admin")) {
                    list.add(criteriaBuilder.and(
                            getPredicate("userName", userVO.getUsername(), root, criteriaBuilder)
                    ));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        };
        Page<Holiday> all = holidayRepository.findAll(specification, pageable);
        Page<HolidayVO> map = all.map(x -> TransferUtil.transferTo(x, HolidayVO.class));
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()),
                all.getTotalElements());
        return map;
    }

    /**
     * 获取查询条件中的谓词
     *
     * @param key
     * @param root
     * @param criteriaBuilder
     * @return
     */
    private Predicate getPredicate(String key, String value, Root<Holiday> root, CriteriaBuilder criteriaBuilder) {
        Path<String> path = root.get(key);
        Predicate predicate = criteriaBuilder.like(path, "%" + value + "%");
        return predicate;
    }
}
