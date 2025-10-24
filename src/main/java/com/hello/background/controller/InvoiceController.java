package com.hello.background.controller;

import com.hello.background.service.InvoiceService;
import com.hello.background.vo.InvoiceVO;
import com.hello.background.vo.InvoiceVOPageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

/**
 * 发票控制器
 *
 * @author wuketao
 * @date 2020/2/3
 * @Description
 */
@RestController
@RequestMapping("invoice")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    /**
     * 保存
     *
     * @param vo
     * @return
     */
    @PostMapping("save")
    public InvoiceVO save(@RequestBody InvoiceVO vo, HttpSession session) {
        return invoiceService.save(vo);
    }

    /**
     * 查询分页
     *
     * @return
     */
    @PostMapping("queryPage")
    public Page<InvoiceVO> queryPage(@RequestBody InvoiceVOPageRequest request) {
        return invoiceService.queryPage(request);
    }

    /**
     * 通过主键删除
     *
     * @return
     */
    @GetMapping("deleteById")
    public String deleteById(Integer id) {
        return invoiceService.deleteById(id);
    }

    /**
     * 获取未付款金额
     *
     * @return
     */
    @GetMapping("getNoPaymentSum")
    public BigDecimal getNoPaymentSum() {
        return invoiceService.getNoPaymentSum();
    }
}
