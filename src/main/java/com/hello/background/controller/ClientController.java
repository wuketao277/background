package com.hello.background.controller;

import com.hello.background.service.ClientContractService;
import com.hello.background.service.ClientService;
import com.hello.background.vo.ClientContractVO;
import com.hello.background.vo.ClientVO;
import com.hello.background.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@RequestMapping("client")
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientContractService clientContractService;

    /**
     * 保存
     *
     * @param vo
     * @return
     */
    @PostMapping("save")
    public ClientVO save(@RequestBody ClientVO vo, HttpSession session) {
        UserVO user = (UserVO) session.getAttribute("user");
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
        search = "%" + search + "%";
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

    /**
     * 获取全部
     *
     * @return
     */
    @GetMapping("findAll")
    public List<ClientVO> findAll() {
        return clientService.findAll();
    }

    /**
     * 获取全部
     *
     * @return
     */
    @GetMapping("findAllOrderByChineseName")
    public List<ClientVO> findAllOrderByChineseName() {
        return clientService.findAllOrderByChineseName();
    }

    /**
     * 保存客户合同
     *
     * @param vo
     * @return
     */
    @PostMapping("saveContract")
    public ClientContractVO saveContract(@RequestBody ClientContractVO vo) {
        return clientContractService.save(vo);
    }

    /**
     * 查询客户合同信息
     *
     * @param clientId
     * @return
     */
    @GetMapping("findContractByClientId")
    public List<ClientContractVO> findContractByClientId(Integer clientId) {
        return clientContractService.findByClientId(clientId);
    }

    /**
     * 通过id查询客户合同信息
     *
     * @param id 客户id
     * @return 客户
     */
    @GetMapping("findContractByClientContractId")
    public ClientContractVO findContractByClientContractId(Integer id) {
        return clientContractService.findById(id);
    }
}
