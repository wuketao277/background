package com.hello.background.service;

import com.hello.background.constant.JobTypeEnum;
import com.hello.background.domain.Client;
import com.hello.background.domain.ClientExt;
import com.hello.background.repository.ClientExtRepository;
import com.hello.background.repository.ClientRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.ClientVO;
import com.hello.background.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuketao
 * @date 2019/12/29
 * @Description
 */
@Transactional
@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientExtRepository clientExtRepository;

    public ClientVO save(ClientVO vo) {
        // 保存客户基本信息
        Client client = vo.toClient();
        client = clientRepository.save(client);
        // 保存客户扩展信息
        ClientExt clientExt = TransferUtil.transferTo(vo, ClientExt.class);
        clientExt.setId(client.getId());
        clientExtRepository.save(clientExt);
        return new ClientVO(client, clientExt);
    }

    /**
     * 查询分页
     *
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    public Page<ClientVO> queryPage(String search, Integer currentPage, Integer pageSize, UserVO user) {
        Pageable pageable = new PageRequest(currentPage - 1, pageSize, Sort.Direction.DESC, "id");
        Specification<Client> specification = new Specification<Client>() {
            @Override
            public Predicate toPredicate(Root<Client> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                list.add(criteriaBuilder.and(criteriaBuilder.or(getPredicate("chineseName", search, root, criteriaBuilder))));
                // 兼职用户，只能查看指定的客户信息
                if (user != null && JobTypeEnum.PARTTIME.compareTo(user.getJobType()) == 0) {
                    list.add(criteriaBuilder.and(getPredicate("parttimers", user.getUsername(), root, criteriaBuilder)));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        };
        Page<Client> page = clientRepository.findAll(specification, pageable);
        Page<ClientVO> map = page.map(c -> {
            ClientExt clientExt = clientExtRepository.queryById(c.getId());
            ClientVO vo = new ClientVO(c, clientExt);
            return vo;
        });
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()), map.getTotalElements());
        return map;
    }

    /**
     * 通过id 获取客户对象
     *
     * @param id 客户id
     * @return
     */
    public ClientVO queryById(Integer id, UserVO user) {
        Client client = clientRepository.queryById(id);
        ClientExt clientExt = clientExtRepository.queryById(client.getId());
        ClientVO vo = new ClientVO(client, clientExt);
        // 兼职用户，只能查看指定的客户信息
        if (user != null && JobTypeEnum.PARTTIME.compareTo(user.getJobType()) == 0) {
            if (vo.getParttimers().isEmpty() || !vo.getParttimers().contains(user.getUsername())) {
                return null;
            }
        }
        return vo;
    }

    /**
     * 获取全部客户信息
     *
     * @return
     */
    public List<ClientVO> findAll(UserVO user) {
        Specification<Client> specification = new Specification<Client>() {
            @Override
            public Predicate toPredicate(Root<Client> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                // 兼职用户，只能查看指定的客户信息
                if (user != null && JobTypeEnum.PARTTIME.compareTo(user.getJobType()) == 0) {
                    list.add(criteriaBuilder.and(getPredicate("parttimers", user.getUsername(), root, criteriaBuilder)));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        };
        List<Client> all = clientRepository.findAll(specification);
        return all.stream().map(c -> {
            ClientExt clientExt = clientExtRepository.queryById(c.getId());
            ClientVO vo = new ClientVO(c, clientExt);
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 获取全部客户信息
     *
     * @return
     */
    public List<ClientVO> findAllOrderByChineseName(UserVO user) {
        Sort sort = new Sort(Sort.Direction.ASC, "chineseName");
        Specification<Client> specification = new Specification<Client>() {
            @Override
            public Predicate toPredicate(Root<Client> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                // 兼职用户，只能查看指定的客户信息
                if (user != null && JobTypeEnum.PARTTIME.compareTo(user.getJobType()) == 0) {
                    list.add(criteriaBuilder.and(getPredicate("parttimers", user.getUsername(), root, criteriaBuilder)));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        };
        List<Client> all = clientRepository.findAll(specification, sort);
        return all.stream().map(c -> {
            ClientExt clientExt = clientExtRepository.queryById(c.getId());
            ClientVO vo = new ClientVO(c, clientExt);
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 获取查询条件中的谓词
     *
     * @param key
     * @param root
     * @param criteriaBuilder
     * @return
     */
    private Predicate getPredicate(String key, String value, Root<Client> root, CriteriaBuilder criteriaBuilder) {
        Path<String> path = root.get(key);
        Predicate predicate = criteriaBuilder.like(path, "%" + value + "%");
        return predicate;
    }
}
