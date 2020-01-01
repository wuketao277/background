package com.hello.background.service;

import com.google.common.base.Strings;
import com.hello.background.domain.Client;
import com.hello.background.repository.ClientRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.ClientVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author wuketao
 * @date 2019/12/29
 * @Description
 */
@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public ClientVO save(ClientVO vo) {
        Client client = (Client) TransferUtil.transferTo(vo, Client.class);
        client = clientRepository.save(client);
        return (ClientVO) TransferUtil.transferTo(client, ClientVO.class);
    }

    /**
     * 查询分页
     *
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    public Page<ClientVO> queryPage(String search, Integer currentPage, Integer pageSize) {
        Pageable pageable = new PageRequest(currentPage - 1, pageSize);
        Page<Client> page = null;
        long total = 0;
        if (Strings.isNullOrEmpty(search)) {
            page = clientRepository.findAll(pageable);
            total = clientRepository.count();
        } else {
            page = clientRepository.findByEnglishNameLikeOrChineseNameLike(search, search, pageable);
            total = clientRepository.countByEnglishNameLikeOrChineseNameLike(search, search);
        }
        Page<ClientVO> map = page.map(x -> (ClientVO) TransferUtil.transferTo(x, ClientVO.class));
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()),
                total);
        return map;
    }

    /**
     * 通过id 获取客户对象
     *
     * @param id 客户id
     * @return
     */
    public ClientVO queryById(Integer id) {
        Client client = clientRepository.queryById(id);
        return (ClientVO) TransferUtil.transferTo(client, ClientVO.class);
    }
}
