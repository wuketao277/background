package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 资源 视图对象
 *
 * @author wuketao
 * @date 2019/12/7
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceVO {
    /**
     * 资源id
     */
    private Integer id;
    /**
     * 资源中文名称
     */
    private String name;
    /**
     * 角色集合
     */
    private List<RoleVO> roleVOList;
    /**
     * 请求url
     */
    private String url;
}
