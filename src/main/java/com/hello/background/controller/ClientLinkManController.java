package com.hello.background.controller;

import com.hello.background.service.ClientLinkManService;
import com.hello.background.vo.ClientLinkManVO;
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
@RequestMapping("clientlinkman")
public class ClientLinkManController {

    @Autowired
    private ClientLinkManService clientLinkManService;

    /**
     * 保存
     *
     * @param vo
     * @return
     */
    @PostMapping("save")
    public ClientLinkManVO save(ClientLinkManVO vo) {
        return clientLinkManService.save(vo);
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
    public Page<ClientLinkManVO> queryPage(String search, Integer currentPage, Integer pageSize) {
        return clientLinkManService.queryPage(search, currentPage, pageSize);
    }
}
