package com.hello.background.service;

import com.hello.background.domain.SchoolType;
import com.hello.background.repository.SchoolTypeRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.SchoolTypeVO;
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
 * @author wuketao
 * @date 2025/10/10
 * @Description
 */
@Transactional
@Service
public class SchoolTypeService {

    @Autowired
    private SchoolTypeRepository schoolTypeRepository;

    /**
     * 保存学校类型信息
     *
     * @param vo
     * @return
     */
    public SchoolTypeVO save(SchoolTypeVO vo) {
        SchoolType schoolType = TransferUtil.transferTo(vo, SchoolType.class);
        schoolType = schoolTypeRepository.save(schoolType);
        return TransferUtil.transferTo(schoolType, SchoolTypeVO.class);
    }

    /**
     * 查询分页
     *
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    public Page<SchoolTypeVO> queryPage(String search, Integer currentPage, Integer pageSize) {
        Pageable pageable = new PageRequest(currentPage - 1, pageSize, Sort.Direction.DESC, "id");
        Specification<SchoolType> specification = new Specification<SchoolType>() {
            @Override
            public Predicate toPredicate(Root<SchoolType> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                list.add(criteriaBuilder.and(criteriaBuilder.or(getPredicate("schoolName", search, root, criteriaBuilder))));
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        };
        Page<SchoolType> page = schoolTypeRepository.findAll(specification, pageable);
        Page<SchoolTypeVO> map = page.map(c -> {
            return TransferUtil.transferTo(c, SchoolTypeVO.class);
        });
        map = new PageImpl<>(map.getContent(), new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()), map.getTotalElements());
        return map;
    }

    /**
     * 通过id 获取
     *
     * @param id
     * @return
     */
    public SchoolTypeVO queryById(Integer id) {
        Optional<SchoolType> optional = schoolTypeRepository.findById(id);
        return optional.map(c -> {
            return TransferUtil.transferTo(c, SchoolTypeVO.class);
        }).orElse(null);
    }

    /**
     * 获取查询条件中的谓词
     *
     * @param key
     * @param root
     * @param criteriaBuilder
     * @return
     */
    private Predicate getPredicate(String key, String value, Root<SchoolType> root, CriteriaBuilder criteriaBuilder) {
        Path<String> path = root.get(key);
        Predicate predicate = criteriaBuilder.like(path, "%" + value + "%");
        return predicate;
    }

    /**
     * 检查是否为公立学校
     *
     * @param schoolName
     * @return
     */
    public String checkIsPublic(String schoolName) {
        SchoolType schoolType = schoolTypeRepository.findBySchoolName(schoolName);
        if (null == schoolType) {
            return "未知院校";
        } else {
            return schoolType.getIsPublic() ? "公办" : "民办";
        }
    }
}
