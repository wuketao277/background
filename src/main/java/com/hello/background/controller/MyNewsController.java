package com.hello.background.controller;

import com.alibaba.fastjson.JSONObject;
import com.hello.background.domainservice.MyNewsDomainService;
import com.hello.background.vo.MyNewsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 创建新闻
     *
     * @param vo 新闻视图对象
     * @return
     */
    @PostMapping("create")
    public JSONObject create(MyNewsVO vo) {
        JSONObject jo = new JSONObject();
        if (myNewsDomainService.create(vo)) {
            jo.put("flag", true);
            jo.put("msg", "保存成功！");
        } else {
            jo.put("flag", false);
            jo.put("msg", "保存失败！");
        }
        return jo;
    }
}
