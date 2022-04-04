package com.hello.background.service;

import com.google.common.base.Strings;
import com.hello.background.domain.UploadFile;
import com.hello.background.domain.UserRole;
import com.hello.background.repository.UploadFileRepository;
import com.hello.background.repository.UserRoleRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.UploadFileVO;
import com.hello.background.vo.UserVO;
import com.mysql.jdbc.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 上传文件 服务
 *
 * @author wuketao
 * @date 2020/2/5
 * @Description
 */
@Transactional
@Slf4j
@Service
public class UploadFileService {
    @Autowired
    private UploadFileRepository uploadFileRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    /**
     * 文件仓库路径
     */
    @Value("${file_store_path}")
    private String fileStorePath = null;

    /**
     * 上传文件
     *
     * @param request
     * @return
     */
    public List<UploadFileVO> uploadFile(HttpServletRequest request, UserVO userVO) {
        List<UploadFileVO> list = new ArrayList<>();
        try {
            StandardMultipartHttpServletRequest multipartRequest = (StandardMultipartHttpServletRequest) request;
            Map<String, String[]> parameterMap = multipartRequest.getParameterMap();
            Integer tableId = null;
            if (parameterMap.containsKey("tableId")) {
                tableId = new Integer(parameterMap.get("tableId")[0]);
            }
            String tableName = null;
            if (parameterMap.containsKey("tableName")) {
                tableName = parameterMap.get("tableName")[0];
            }
            String hasSensitiveInfomation = null;
            if (parameterMap.containsKey("hasSensitiveInfomation")) {
                hasSensitiveInfomation = parameterMap.get("hasSensitiveInfomation")[0];
            }
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            log.info("fileStorePath:" + fileStorePath);
            if (!fileMap.isEmpty() && !StringUtils.isNullOrEmpty(fileStorePath)) {
                for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
                    String originalFileName = entry.getValue().getOriginalFilename();
                    log.info("originalFileName:" + originalFileName);
                    //获取item中的上传文件的输入流
                    try (InputStream inputStream = entry.getValue().getInputStream()) {
                        String uuid = UUID.randomUUID().toString();
                        if (!fileStorePath.endsWith("/")) {
                            fileStorePath += "/";
                        }
                        OutputStream outputStream = new FileOutputStream(fileStorePath + uuid + originalFileName);
                        int bytesWritten = 0;
                        int byteCount = 0;
                        byte[] bytes = new byte[1024 * 1024 * 100];
                        while ((byteCount = inputStream.read(bytes)) != -1) {
                            outputStream.write(bytes, bytesWritten, byteCount);
                            bytesWritten += byteCount;
                        }
                        outputStream.close();
                        // 将文件信息写入数据库
                        UploadFile uploadFile = new UploadFile();
                        uploadFile.setCreateTime(LocalDateTime.now());
                        uploadFile.setCreateUserName(userVO.getUsername());
                        uploadFile.setCreateRealName(userVO.getRealname());
                        uploadFile.setUuid(uuid);
                        uploadFile.setOriginalFileName(originalFileName);
                        if (null != tableId) {
                            uploadFile.setRelativeTableId(tableId);
                        }
                        if (null != tableName) {
                            uploadFile.setRelativeTableName(tableName);
                        }
                        if (null != hasSensitiveInfomation) {
                            uploadFile.setHasSensitiveInfomation(hasSensitiveInfomation);
                        }
                        UploadFile saved = uploadFileRepository.save(uploadFile);
                        list.add(TransferUtil.transferTo(saved, UploadFileVO.class));
                    } catch (Exception ex) {
                        log.error("{}", ex);
                    }
                }
            }
        } catch (Exception ex) {
            log.error("{}", ex);
        }
        return list;
    }

    /**
     * 文件下载前检查
     *
     * @param uuid
     * @param userVO
     * @return
     */
    public String downloadPreCheck(String uuid, UserVO userVO) {
        UploadFile uploadFile = uploadFileRepository.findByUuid(uuid);
        if (uploadFile == null) {
            return "文件不存在！";
        }
        if ("Y".equals(uploadFile.getHasSensitiveInfomation())) {
            // 包含敏感信息的文件，要进行下载检查
            if (!userVO.getUsername().equals(uploadFile.getCreateUserName())) {
                // 当前用户不是文件的上传人，需要检查是否是管理员。
                List<UserRole> userRoleList = userRoleRepository.findByUserName(userVO.getUsername());
                if (!userRoleList.stream().anyMatch(ur -> "admin".equals(ur.getRoleName()))) {
                    // 也不是管理员
                    return "包含敏感信息的文件，只有上传人或管理员可以下载！";
                }
            }
        }
        // 返回空表示可以下载
        return "";
    }

    /**
     * 下载文件
     *
     * @param uuid
     * @param response
     */
    public void downloanFile(String uuid, HttpServletResponse response) {
        try {
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;

            UploadFile uploadFile = uploadFileRepository.findByUuid(uuid);
            if (null != uploadFile && !StringUtils.isNullOrEmpty(fileStorePath)) {
                String originalFileName = uploadFile.getOriginalFileName();
                //获取下载文件路径
                if (!fileStorePath.endsWith("/")) {
                    fileStorePath += "/";
                }
                String downLoadPath = fileStorePath + uuid + originalFileName;
                //获取文件的长度
                long fileLength = new File(downLoadPath).length();
                //设置文件输出类型
                response.setContentType("application/octet-stream");
                response.setHeader("Content-disposition", "attachment; filename="
                        + new String(originalFileName.getBytes("utf-8"), "ISO8859-1"));
                //设置输出长度
                response.setHeader("Content-Length", String.valueOf(fileLength));
                //获取输入流
                bis = new BufferedInputStream(new FileInputStream(downLoadPath));
                //输出流
                bos = new BufferedOutputStream(response.getOutputStream());
                byte[] buff = new byte[2048];
                int bytesRead;
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
                //关闭流
                bis.close();
                bos.close();
            }
        } catch (Exception ex) {
            log.error("{}", ex);
        }
    }

    /**
     * 通过uuid查找上传文件记录
     *
     * @param uuid 文件唯一编号
     * @return 上传文件记录
     */
    public UploadFileVO findByUuid(String uuid) {
        UploadFile uploadFile = uploadFileRepository.findByUuid(uuid);
        return TransferUtil.transferTo(uploadFile, UploadFileVO.class);
    }

    /**
     * 通过关联表id和关联表名称获取上传文件记录集合
     *
     * @param relativeTableId   关联表id
     * @param relativeTableName 关联表名称
     * @return 上传文件记录集合
     */
    public List<UploadFileVO> findByRelativeTableIdAndRelativeTableName(Integer relativeTableId, String relativeTableName) {
        List<UploadFile> list = uploadFileRepository.findByRelativeTableIdAndRelativeTableName(relativeTableId, relativeTableName);
        return list.stream().map(uf -> TransferUtil.transferTo(uf, UploadFileVO.class)).collect(Collectors.toList());
    }

    /**
     * 通过Id删除
     *
     * @param id
     */
    public String deleteById(Integer id, UserVO userVO) {
        Optional<UploadFile> optionalUploadFile = uploadFileRepository.findById(id);
        if (!optionalUploadFile.isPresent()) {
            return "文件不存在！";
        }
        UploadFile uploadFile = optionalUploadFile.get();
        if (!userVO.getUsername().equals(uploadFile.getCreateUserName())) {
            // 当前用户不是文件的上传人，需要检查是否是管理员。
            List<UserRole> userRoleList = userRoleRepository.findByUserName(userVO.getUsername());
            if (!userRoleList.stream().anyMatch(ur -> "admin".equals(ur.getRoleName()))) {
                // 也不是管理员
                return "文件只能被上传人或管理员删除！";
            }
        }
        uploadFileRepository.deleteById(id);
        return "";
    }

    /**
     * 搜索
     *
     * @param search
     * @param currentPage
     * @param pageSize
     * @return
     */
    public Page<UploadFileVO> findByOriginalFileName(String search, Integer currentPage, Integer pageSize) {
        Pageable pageable = new PageRequest(currentPage - 1, pageSize, Sort.Direction.DESC, "id");
        Page<UploadFile> uploadFilePage = null;
        long total = 0;
        if (Strings.isNullOrEmpty(search)) {
            uploadFilePage = uploadFileRepository.findAll(pageable);
            total = uploadFileRepository.count();
        } else {
            uploadFilePage = uploadFileRepository.findByOriginalFileNameLike(search, pageable);
            total = uploadFileRepository.countByOriginalFileNameLike(search);
        }
        Page<UploadFileVO> map = uploadFilePage.map(x -> TransferUtil.transferTo(x, UploadFileVO.class));
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()),
                total);
        return map;
    }
}
