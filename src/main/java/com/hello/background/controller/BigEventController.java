package com.hello.background.controller;

import com.hello.background.service.BigEventService;
import com.hello.background.vo.BigEventVO;
import com.hello.background.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 大事件
 */
@Slf4j
@RestController
@RequestMapping("bigEvent")
public class BigEventController {
    @Autowired
    private BigEventService bigEventService;

    /**
     * 保存
     *
     * @param vo
     * @return
     */
    @PostMapping("save")
    public BigEventVO save(@RequestBody BigEventVO vo, HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        return bigEventService.save(vo, userVO);
    }

    /**
     * 删除
     *
     * @param id
     */
    @PostMapping("deleteById")
    public void deleteById(Integer id) {
        bigEventService.deleteById(id);
    }

    /**
     * 查询ById
     *
     * @param id
     * @return
     */
    @PostMapping("findById")
    public BigEventVO findById(Integer id) {
        return bigEventService.findById(id);
    }

    /**
     * 分页查询
     *
     * @param search
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("query")
    public Page<BigEventVO> query(@RequestParam String search, @RequestParam Integer currentPage, @RequestParam Integer pageSize) {
        return bigEventService.queryPage(search, currentPage, pageSize);
    }
}
