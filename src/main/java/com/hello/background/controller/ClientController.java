package com.hello.background.controller;

import com.hello.background.domain.User;
import com.hello.background.service.ClientService;
import com.hello.background.vo.ClientVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

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
    public ClientVO save(@RequestBody ClientVO vo, HttpSession session) {
        User user = (User) session.getAttribute("user");
        vo.setCreateTime(LocalDateTime.now());
        vo.setCreateUserName(user.getUsername());
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

    /**
     * 通过id查询客户
     *
     * @param id 客户id
     * @return 客户
     */
    @GetMapping("queryById")
    public ClientVO queryById(Integer id) {
        return clientService.queryById(id);
    }
}
