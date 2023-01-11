package com.hello.background.controller;

import com.hello.background.service.ClientService;
import com.hello.background.service.SuccessfulPermService;
import com.hello.background.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Optional;

/**
 * @author wuketao
 * @date 2020/2/3
 * @Description
 */
@RestController
@RequestMapping("successfulPerm")
public class SuccessfulPermController {
    @Autowired
    private SuccessfulPermService successfulPermService;
    @Autowired
    private ClientService clientService;

    /**
     * 保存
     *
     * @param vo
     * @return
     */
    @PostMapping("save")
    public SuccessfulPermVO save(@RequestBody SuccessfulPermVO vo, HttpSession session) {
        UserVO user = (UserVO) session.getAttribute("user");
        vo.setUpdateTime(new Date());
        vo.setUpdateUserName(user.getUsername());
        if (!StringUtils.isEmpty(vo.getClientId())) {
            vo.setClientName(Optional.ofNullable(clientService.queryById(vo.getClientId())).map(x -> x.getChineseName()).orElse(""));
        }
        return successfulPermService.save(vo);
    }

    /**
     * 查询分页
     *
     * @return
     */
    @PostMapping("queryPage")
    public Page<SuccessfulPermVO> queryPage(@RequestBody SuccessfulPermVOPageRequest request, HttpSession session) {
        return successfulPermService.queryPage(request, session);
    }

    /**
     * 查询统计
     *
     * @return
     */
    @PostMapping("queryStatistics")
    public SuccessfulCaseStatisticsResponse queryStatistics(@RequestBody SuccessfulPermVOPageRequest request, HttpSession session) {
        return successfulPermService.queryStatistics(request, session);
    }

    /**
     * 通过主键删除
     *
     * @param request
     * @return
     */
    @PostMapping("deleteById")
    public String deleteById(@RequestBody DeleteSuccessfulPermRequest request) {
        return successfulPermService.deleteById(request.getId());
    }
}
