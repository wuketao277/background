package com.hello.background.controller;

import com.hello.background.service.PRCService;
import com.hello.background.vo.PRCVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author wuketao
 * @date 2019/12/14
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("prc")
public class PRCController {

    @Autowired
    private PRCService prcService;

    /**
     * 保存PRC信息
     *
     * @param vo
     * @return
     */
    @PostMapping("save")
    public PRCVO save(@RequestBody PRCVO vo) {
        return prcService.save(vo);
    }

    /**
     * 通过主键查找PRC信息
     *
     * @param id 主键
     * @return PRC信息
     */
    @GetMapping("findById")
    public PRCVO findById(@RequestParam Integer id) {
        return prcService.findById(id);
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @GetMapping("deleteById")
    public boolean deleteById(Integer id) {
        prcService.deleteById(id);
        return true;
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
    public Page<PRCVO> queryPage(String search, Integer currentPage, Integer pageSize) {
        return prcService.queryPage(search, currentPage, pageSize);
    }
}
