package com.hello.background.service;

import com.google.common.base.Strings;
import com.hello.background.domain.Client;
import com.hello.background.domain.ClientLinkMan;
import com.hello.background.repository.ClientLinkManRepository;
import com.hello.background.repository.ClientRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.ClientLinkManSimpleVO;
import com.hello.background.vo.ClientLinkManVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wuketao
 * @date 2019/12/29
 * @Description
 */
@Transactional
@Service
public class ClientLinkManService {
    @Autowired
    private ClientLinkManRepository clientLinkManRepository;
    @Autowired
    private ClientRepository clientRepository;

    private ClientLinkManVO fromDoToVo(ClientLinkMan clientLinkMan) {
        ClientLinkManVO vo = TransferUtil.transferTo(clientLinkMan, ClientLinkManVO.class);
        Optional<Client> clientOptional = clientRepository.findById(clientLinkMan.getClientId());
        if (clientOptional.isPresent()) {
            vo.setClientName(clientOptional.get().getChineseName());
        }
        return vo;
    }

    public ClientLinkManVO save(ClientLinkManVO vo) {
        ClientLinkMan clm = TransferUtil.transferTo(vo, ClientLinkMan.class);
        clm = clientLinkManRepository.save(clm);
        return fromDoToVo(clm);
    }

    /**
     * 通过 客户id查找所有联系人
     *
     * @param clientId 客户id
     * @return
     */
    public List<ClientLinkManVO> queryByClientId(Integer clientId) {
        List<ClientLinkMan> list = clientLinkManRepository.queryByClientId(clientId);
        return list.stream().map(man -> TransferUtil.transferTo(man, ClientLinkManVO.class)).collect(Collectors.toList());
    }

    /**
     * 查询分页
     *
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    public Page<ClientLinkManVO> queryPage(String search, Integer currentPage, Integer pageSize) {
        Pageable pageable = new PageRequest(currentPage - 1, pageSize);
        Page<ClientLinkMan> casePage = null;
        long total = 0;
        if (Strings.isNullOrEmpty(search)) {
            casePage = clientLinkManRepository.findAll(pageable);
            total = clientLinkManRepository.count();
        } else {
            casePage = clientLinkManRepository.findByEnglishNameLikeOrChineseNameLike(search, search, pageable);
            total = clientLinkManRepository.countByEnglishNameLikeOrChineseNameLike(search, search);
        }
        Page<ClientLinkManVO> map = casePage.map(x -> fromDoToVo(x));
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()),
                total);
        return map;
    }

    /**
     * 获取所有联系人
     *
     * @return
     */
    public List<ClientLinkManVO> queryAll() {
        List<String> properties = new ArrayList<>();
        properties.add("englishName");
        properties.add("chineseName");
        Sort sort = new Sort(Sort.Direction.ASC, properties);
        List<ClientLinkMan> list = clientLinkManRepository.findAll(sort);
        List<ClientLinkManVO> clientLinkManVOList = list.stream().map(c -> TransferUtil.transferTo(c, ClientLinkManVO.class)).collect(Collectors.toList());
        // 获取全部客户信息
        List<Client> clientList = clientRepository.findAll(new Specification<Client>() {
            @Override
            public Predicate toPredicate(Root<Client> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        });
        // 每个hr添加客户名称
        clientLinkManVOList.forEach(m -> {
            if (null != m.getClientId()) {
                Optional<Client> clientOptional = clientList.stream().filter(c -> m.getClientId().equals(c.getId())).findFirst();
                if (clientOptional.isPresent()) {
                    m.setClientName(clientOptional.get().getChineseName());
                }
            }
        });
        return clientLinkManVOList;
    }

    /**
     * 获取所有联系人
     *
     * @return
     */
    public List<ClientLinkManSimpleVO> queryAllForSimple() {
        List<ClientLinkMan> list = clientLinkManRepository.findAll();
        List<ClientLinkManSimpleVO> manVOList = list.stream().map(c -> {
            ClientLinkManSimpleVO simpleVO = TransferUtil.transferTo(c, ClientLinkManSimpleVO.class);
            if (!Strings.isNullOrEmpty(c.getEnglishName()) && !Strings.isNullOrEmpty(c.getChineseName())) {
                simpleVO.setName(String.format("%s - %s", c.getEnglishName(), c.getChineseName()));
            } else if (!Strings.isNullOrEmpty(c.getEnglishName())) {
                simpleVO.setName(c.getEnglishName());
            } else if (!Strings.isNullOrEmpty(c.getChineseName())) {
                simpleVO.setName(c.getChineseName());
            }
            return simpleVO;
        }).collect(Collectors.toList());
        // 按中文名排序
        manVOList.sort(Comparator.comparing(ClientLinkManSimpleVO::getName));
        return manVOList;
    }
}
