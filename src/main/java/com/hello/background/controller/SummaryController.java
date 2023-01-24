package com.hello.background.controller;

import com.hello.background.service.SummaryService;
import com.hello.background.vo.PipelineVO;
import com.hello.background.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 摘要控制器
 *
 * @author wuketao
 * @date 2023/1/20
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("summary")
public class SummaryController {

    @Autowired
    private SummaryService summaryService;

    /**
     * 查询Pipeline
     *
     * @param session
     * @return
     */
    @GetMapping("queryPipeline")
    public List<PipelineVO> queryPipeline(@RequestParam("range") String range, HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        return summaryService.queryPipeline(range, userVO);
    }
}
