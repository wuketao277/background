package com.hello.background.controller;

import com.hello.background.service.CostInvoiceUsedService;
import com.hello.background.vo.CostInvoiceUsedVO;
import com.hello.background.vo.CostInvoiceUsedVOPageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 成本发票使用情况控制器
 *
 * @author wuketao
 * @date 2026/02/22
 * @Description
 */
@RestController
@RequestMapping("costInvoiceUsed")
public class CostInvoiceUsedController {
    @Autowired
    private CostInvoiceUsedService costInvoiceUsedService;

    /**
     * 保存
     *
     * @param vo
     * @return
     */
    @PostMapping("save")
    public CostInvoiceUsedVO save(@RequestBody CostInvoiceUsedVO vo, HttpSession session) {
        return costInvoiceUsedService.save(vo);
    }

    /**
     * 查询分页
     *
     * @return
     */
    @PostMapping("queryPage")
    public Page<CostInvoiceUsedVO> queryPage(@RequestBody CostInvoiceUsedVOPageRequest request) {
        return costInvoiceUsedService.queryPage(request);
    }

    /**
     * 通过主键删除
     *
     * @return
     */
    @GetMapping("deleteById")
    public void deleteById(Integer id) {
        costInvoiceUsedService.deleteById(id);
    }
}
