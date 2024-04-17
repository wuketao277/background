package com.hello.background.service;

import com.google.common.base.Strings;
import com.hello.background.domain.Client;
import com.hello.background.domain.ClientExt;
import com.hello.background.repository.ClientExtRepository;
import com.hello.background.repository.ClientRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.ClientVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Client client = TransferUtil.transferTo(vo, Client.class);
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
        Page<ClientVO> map = page.map(c -> {
            ClientExt clientExt = clientExtRepository.queryById(c.getId());
            ClientVO vo = new ClientVO(c, clientExt);
            return vo;
        });
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
        ClientExt clientExt = clientExtRepository.queryById(client.getId());
        return new ClientVO(client, clientExt);
    }

    /**
     * 获取全部客户信息
     *
     * @return
     */
    public List<ClientVO> findAll() {
        List<Client> all = clientRepository.findAll();
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
    public List<ClientVO> findAllOrderByChineseName() {
        Sort sort = new Sort(Sort.Direction.ASC, "chineseName");
        List<Client> all = clientRepository.findAll(sort);
        return all.stream().map(c -> {
            ClientExt clientExt = clientExtRepository.queryById(c.getId());
            ClientVO vo = new ClientVO(c, clientExt);
            return vo;
        }).collect(Collectors.toList());
    }
}
