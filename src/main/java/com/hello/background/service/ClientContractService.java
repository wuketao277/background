package com.hello.background.service;

import com.hello.background.domain.ClientContract;
import com.hello.background.repository.ClientContractRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.ClientContractVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
}
