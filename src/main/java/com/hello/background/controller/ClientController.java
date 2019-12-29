package com.hello.background.controller;

import com.hello.background.service.ClientService;
import com.hello.background.vo.ClientVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuketao
 * @date 2019/12/29
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    /**
     * 保存
     *
     * @param vo
     * @return
     */
    @PostMapping("save")
    public ClientVO save(ClientVO vo) {
        return clientService.save(vo);
    }

    /**
     * 查询分页
     *
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    @GetMapping("queryPage")
    public Page<ClientVO> queryPage(String search, Integer currentPage, Integer pageSize) {
        return clientService.queryPage(search, currentPage, pageSize);
    }
}
