package com.hello.background.controller;

import com.hello.background.service.CostInvoiceService;
import com.hello.background.vo.CostInvoiceVO;
import com.hello.background.vo.CostInvoiceVOPageRequest;
import com.hello.background.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 成本发票控制器
 *
 * @author wuketao
 * @date 2026/02/22
 * @Description
 */
@RestController
@RequestMapping("costInvoice")
public class CostInvoiceController {
    @Autowired
    private CostInvoiceService costInvoiceService;

    /**
     * 保存
     *
     * @param vo
     * @return
     */
    @PostMapping("save")
    public CostInvoiceVO save(@RequestBody CostInvoiceVO vo, HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        return costInvoiceService.save(vo, userVO);
    }

    /**
     * 查询分页
     *
     * @return
     */
    @PostMapping("queryPage")
    public Page<CostInvoiceVO> queryPage(@RequestBody CostInvoiceVOPageRequest request) {
        return costInvoiceService.queryPage(request);
    }

    /**
     * 通过主键删除
     *
     * @return
     */
    @GetMapping("deleteById")
    public void deleteById(Integer id) {
        costInvoiceService.deleteById(id);
    }
}
