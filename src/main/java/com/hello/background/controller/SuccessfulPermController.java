package com.hello.background.controller;

import com.hello.background.service.ClientService;
import com.hello.background.service.SuccessfulPermService;
import com.hello.background.vo.SuccessfulPermVO;
import com.hello.background.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
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
}
