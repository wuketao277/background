package com.hello.background.service;

import com.google.common.base.Strings;
import com.hello.background.domain.Client;
import com.hello.background.domain.ClientLinkMan;
import com.hello.background.repository.ClientLinkManRepository;
import com.hello.background.repository.ClientRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.ClientLinkManVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
}
