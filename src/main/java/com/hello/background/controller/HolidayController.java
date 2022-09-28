package com.hello.background.controller;

import com.hello.background.service.HolidayService;
import com.hello.background.vo.HolidayVO;
import com.hello.background.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 假期控制器
 *
 * @author wuketao
 * @date 2019/12/14
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("holiday")
public class HolidayController {

    @Autowired
    private HolidayService holidayService;

    /**
     * 保存假期信息
     *
     * @param vo
     * @return
     */
    @PostMapping("save")
    public HolidayVO save(@RequestBody HolidayVO vo, HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        return holidayService.save(vo, userVO);
    }


    /**
     * 通过主键查找假期信息
     *
     * @param id 主键
     * @return 候选人信息
     */
    @GetMapping("findById")
    public HolidayVO findById(@RequestParam Integer id) {
        return holidayService.findById(id);
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @GetMapping("deleteById")
    public boolean deleteById(Integer id) {
        holidayService.deleteById(id);
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
    public Page<HolidayVO> queryPage(String search, Integer currentPage, Integer pageSize, HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        return holidayService.queryPage(search, currentPage, pageSize, userVO);
    }
}
