package com.hello.background.controller;

import com.hello.background.service.UploadFileService;
import com.hello.background.vo.UploadFileVO;
import com.hello.background.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author wuketao
 * @date 2020/2/5
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("uploadFile")
public class UploadFileController {
    @Autowired
    private UploadFileService uploadFileService;

    /**
     * 上传文件
     *
     * @param request
     * @return
     */
    @PostMapping("uploadFile")
    public List<UploadFileVO> uploadFile(HttpServletRequest request, HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        return uploadFileService.uploadFile(request, userVO);
    }

    /**
     * 下载前检查
     *
     * @param uuid
     */
    @GetMapping("downloadPreCheck")
    public String downloadPreCheck(@RequestParam String uuid, HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        return uploadFileService.downloadPreCheck(uuid, userVO);
    }

    /**
     * 下载文件
     *
     * @param response
     * @param uuid
     * @throws Exception
     */
    @GetMapping("download")
    public void download(@RequestParam String uuid, HttpServletResponse response) throws Exception {
        uploadFileService.downloanFile(uuid, response);
    }

    /**
     * 通过关联表id和关联表名称获取上传文件记录集合
     *
     * @param relativeTableId   关联表id
     * @param relativeTableName 关联表名称
     * @return 上传文件记录集合
     */
    @GetMapping("findByRelativeTableIdAndRelativeTableName")
    public List<UploadFileVO> findByRelativeTableIdAndRelativeTableName(@RequestParam Integer relativeTableId, @RequestParam String relativeTableName) {
        return uploadFileService.findByRelativeTableIdAndRelativeTableName(relativeTableId, relativeTableName);
    }

    /**
     * 通过uuid删除
     *
     * @param id
     */
    @GetMapping("deleteById")
    public String deleteById(@RequestParam Integer id, HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        return uploadFileService.deleteById(id, userVO);
    }

    /**
     * 查询分页
     *
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    @GetMapping("findByOriginalFileName")
    public Page<UploadFileVO> findByOriginalFileName(String search, Integer currentPage, Integer pageSize) {
        search = "%" + search + "%";
        return uploadFileService.findByOriginalFileName(search, currentPage, pageSize);
    }
}
