package com.hello.background.controller;

import com.hello.background.service.MyNewsDomainService;
import com.hello.background.vo.MyNewsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 新闻领域对象 控制器
 *
 * @author wuketao
 * @date 2019/11/24
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("mynews")
public class MyNewsController {
    @Autowired
    private MyNewsDomainService myNewsDomainService;

    /**
     * 获取所有新闻
     *
     * @return 新闻列表
     */
    @GetMapping("findAll")
    public List<MyNewsVO> findAll() {
        return myNewsDomainService.findAll();
    }

    /**
     * 保存新闻
     *
     * @param vo 新闻视图对象
     * @return
     */
    @PostMapping("saveNews")
    public MyNewsVO saveNews(@RequestBody MyNewsVO vo) {
        vo.setCreateUserId("1");
        vo.setCreateUserName("wuketao");
        vo.setCreateTime(LocalDateTime.now());
        MyNewsVO result = myNewsDomainService.saveNews(vo);
        return result;
    }

    /**
     * 查询新闻，分页
     *
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    @GetMapping("queryNewsPage")
    public Page<MyNewsVO> queryNewsPage(String search, Integer currentPage, Integer pageSize) {
        search = "%" + search + "%";
        return myNewsDomainService.queryNewsPage(search, currentPage, pageSize);
    }
}
