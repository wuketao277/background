package com.hello.background.controller;

import com.hello.background.service.ClientLinkManService;
import com.hello.background.vo.ClientLinkManSimpleVO;
import com.hello.background.vo.ClientLinkManVO;
import com.hello.background.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

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
    public ClientLinkManVO save(@RequestBody ClientLinkManVO vo, HttpSession session) {
        vo.setCreateTime(LocalDateTime.now());
        UserVO user = (UserVO) session.getAttribute("user");
        vo.setCreateUserId(user.getUsername());
        return clientLinkManService.save(vo);
    }

    /**
     * 通过 客户id 查询所有联系人
     *
     * @param clientId 客户id
     * @return 所有联系人
     */
    @GetMapping("queryByClientId")
    public List<ClientLinkManVO> queryByClientId(Integer clientId) {
        return clientLinkManService.queryByClientId(clientId);
    }

    /**
     * 获取所有联系人
     *
     * @return 所有联系人
     */
    @GetMapping("queryAll")
    public List<ClientLinkManVO> queryAll() {
        return clientLinkManService.queryAll();
    }

    /**
     * 获取所有联系人
     *
     * @return 所有联系人
     */
    @GetMapping("queryAllForSimple")
    public List<ClientLinkManSimpleVO> queryAllForSimple() {
        return clientLinkManService.queryAllForSimple();
    }
}
