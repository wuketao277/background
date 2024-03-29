package com.hello.background.controller;

import com.hello.background.constant.RoleEnum;
import com.hello.background.service.MyNewsService;
import com.hello.background.vo.MyNewsVO;
import com.hello.background.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
    private MyNewsService myNewsDomainService;

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
    public MyNewsVO saveNews(@RequestBody MyNewsVO vo, HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        vo.setCreateUserId(String.valueOf(userVO.getId()));
        vo.setCreateUserName(userVO.getUsername());
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

    /**
     * 获取前10条
     *
     * @return
     */
    @GetMapping("findTop10")
    public List<MyNewsVO> findTop10() {
        return myNewsDomainService.findTop10ByPublishOrderByCreateTimeDesc();
    }

    /**
     * 获取前100条
     *
     * @return
     */
    @GetMapping("findTop100")
    public List<MyNewsVO> findTop100() {
        return myNewsDomainService.findTop100ByPublishOrderByCreateTimeDesc();
    }

    /**
     * 通过id删除新闻
     *
     * @param id
     * @param session
     */
    @GetMapping("deleteById")
    public void deleteById(Integer id, HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        if (userVO.getRoles().contains(RoleEnum.ADMIN)) {
            // 管理员才能删除新闻
            myNewsDomainService.deleteById(id);
        }
    }
}
