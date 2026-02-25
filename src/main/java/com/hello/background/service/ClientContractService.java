package com.hello.background.service;

import com.hello.background.domain.ClientContract;
import com.hello.background.repository.ClientContractRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.ClientContractVO;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 客户合同服务
 *
 * @author wuketao
 * @date 2019/12/29
 * @Description
 */
@Transactional
@Service
public class ClientContractService {

    @Autowired
    private ClientContractRepository clientContractRepository;

    public ClientContractVO save(ClientContractVO vo) {
        ClientContract clientContract = TransferUtil.transferTo(vo, ClientContract.class);
        clientContract = clientContractRepository.save(clientContract);
        return TransferUtil.transferTo(clientContract, ClientContractVO.class);
    }

    /**
     * 获取某个客户的全部合同信息
     *
     * @return
     */
    public List<ClientContractVO> findByClientId(Integer clientId) {
        List<ClientContract> all = clientContractRepository.findByClientIdOrderByExpireDate(clientId);
        return all.stream().map(c -> TransferUtil.transferTo(c, ClientContractVO.class)).collect(Collectors.toList());
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    public ClientContractVO findById(Integer id) {
        return TransferUtil.transferTo(clientContractRepository.findById(id), ClientContractVO.class);
    }

    /**
     * 排序合同信息
     *
     * @return
     */
    public List<ClientContractVO> sortContractView() {
        Specification<ClientContract> specification = new Specification<ClientContract>() {
            @Override
            public Predicate toPredicate(Root<ClientContract> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                // 兼职用户，只能查看指定的客户信息
//                if (user != null && JobTypeEnum.PARTTIME.compareTo(user.getJobType()) == 0) {
//                    list.add(criteriaBuilder.and(getPredicate("parttimers", user.getUsername(), root, criteriaBuilder)));
//                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        };
        List<ClientContract> clientContractList = clientContractRepository.findAll(specification);
        return sortContractList(clientContractList).stream().map(c -> TransferUtil.transferTo(c, ClientContractVO.class)).collect(Collectors.toList());
    }

    /**
     * 排序合同信息
     *
     * @return
     */
    public List<ClientContract> sortContractList(List<ClientContract> clientContractList) {
        // 使用 Map 存储 clientId -> ClientContract，key 为 clientId，value 为对应的 ClientContract
        Map<Integer, ClientContract> contractMap = new HashMap<>();

        // 遍历 clientContractList 进行去重处理
        for (ClientContract contract : clientContractList) {
            Integer clientId = contract.getClientId();
            Date expireDate = contract.getExpireDate();

            // 如果 map 中没有该 clientId 或者当前 expireDate 更大，则更新 map
            if (!contractMap.containsKey(clientId) ||
                    expireDate != null && expireDate.after(contractMap.get(clientId).getExpireDate())) {
                contractMap.put(clientId, contract);
            }
        }

        // 将 map 中的 values 转换为 list 并返回
        List<ClientContract> list = new ArrayList<>(contractMap.values());
        list.sort(Comparator.comparing(ClientContract::getContractOrder, Comparator.reverseOrder())
                .thenComparing(ClientContract::getExpireDate, Comparator.nullsFirst(Comparator.naturalOrder())));
        return list;
    }

    /**
     * 设置合同顺序为-1
     *
     * @param clientId
     */
    public void setContractOrderToMinus1(Integer clientId) {
        List<ClientContract> contractList = clientContractRepository.findByClientIdOrderByExpireDate(clientId);
        for (ClientContract contract : contractList) {
            contract.setContractOrder(-1);
            clientContractRepository.save(contract);
        }
    }
}
