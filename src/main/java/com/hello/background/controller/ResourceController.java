package com.hello.background.controller;

import com.hello.background.service.ResourceService;
import com.hello.background.vo.ResourceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuketao
 * @date 2019/12/9
 * @Description
 */
@RestController
@RequestMapping("resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    /**
     * 保存资源
     *
     * @param vo 资源视图对象
     * @return
     */
    @RequestMapping("saveResource")
    public ResourceVO saveResource(@RequestBody ResourceVO vo) {
        return resourceService.saveResource(vo);
    }

    /**
     * 通过id删除资源
     *
     * @param id 资源Id
     */
    @GetMapping("deleteResource")
    public Boolean deleteResource(Integer id) {
        if (id <= 0) {
            return false;
        }
        resourceService.deleteById(id);
        return true;
    }

    /**
     * 查询资源，分页
     *
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    @GetMapping("queryResourcePage")
    public Page<ResourceVO> queryResourcePage(String search, Integer currentPage, Integer pageSize) {
        search = "%" + search + "%";
        return resourceService.queryResourceVOPage(search, currentPage, pageSize);
    }
}
