package com.hello.background.service;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.hello.background.domain.Candidate;
import com.hello.background.repository.CandidateRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.CandidateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wuketao
 * @date 2019/12/14
 * @Description
 */
@Slf4j
@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    /**
     * 保存候选人
     *
     * @param vo
     * @return
     */
    public CandidateVO save(CandidateVO vo) {
        Candidate candidate = TransferUtil.transferTo(vo, Candidate.class);
        return TransferUtil.transferTo(candidateRepository.save(candidate), CandidateVO.class);
    }


    /**
     * 通过id
     *
     * @param id
     */
    public void deleteById(Integer id) {
        candidateRepository.deleteById(id);
    }

    /**
     * 查询分页
     *
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    public Page<CandidateVO> queryCandidatePage(String search, Integer currentPage, Integer pageSize) {
        Pageable pageable = new PageRequest(currentPage - 1, pageSize);
        Page<Candidate> cadidatePage = null;
        long total = 0;
        if (Strings.isNullOrEmpty(search)) {
            cadidatePage = candidateRepository.findAll(pageable);
            total = candidateRepository.count();
        } else {
            cadidatePage = candidateRepository.findByChineseNameLikeOrEnglishNameLikeOrCompanyNameLike(search, search, search, pageable);
            total = candidateRepository.countByChineseNameLikeOrEnglishNameLikeOrCompanyNameLike(search, search, search);
        }
        Page<CandidateVO> map = cadidatePage.map(x -> TransferUtil.transferTo(x, CandidateVO.class));
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()),
                total);
        return map;
    }

    /**
     * 获取所有新闻
     *
     * @return
     */
    public List<CandidateVO> findAll() {
        List<Candidate> all = candidateRepository.findAll();
        return all.stream().map(x -> TransferUtil.transferTo(x, CandidateVO.class)).collect(Collectors.toList());
    }

    public JSONObject analysisUploadFile(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        result.put("flag", true);
        result.put("msg", "");
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            if (fileMap.isEmpty()) {
                result.put("flag", false);
                result.put("msg", "上传文件未空");
                return result;
            }
            for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
                if (!entry.getValue().getOriginalFilename().endsWith(".xlsx")) {
                    result.put("flag", false);
                    result.put("msg", "上传文件必须是后缀为xlsx的Excel文件");
                    return result;
                }
                //获取item中的上传文件的输入流
                InputStream in = entry.getValue().getInputStream();
                ExcelReader reader = new ExcelReader(in, ExcelTypeEnum.XLSX, null, new AnalysisEventListener<List<String>>() {
                    @Override
                    public void invoke(List<String> object, AnalysisContext context) {
                        Field[] declaredFields = Candidate.class.getDeclaredFields();
                        if (declaredFields.length - 1 != object.size()) {
                            result.put("flag", false);
                            result.put("msg", "Excel文件中的列与目标对象的属性不一致");
                        } else {
                            Candidate candidate = new Candidate();
                            candidate.setChineseName(object.get(0));//中文名
                            candidate.setEnglishName(object.get(1));// 英文名
                            if (!StringUtils.isEmpty(object.get(2))) { // 年龄
                                candidate.setAge(Integer.parseInt(object.get(2)));
                            }
                            candidate.setPhoneNo(object.get(3)); // 电话
                            candidate.setEmail(object.get(4)); // 邮箱
                            candidate.setCompanyName(object.get(5)); // 公司
                            candidate.setDepartment(object.get(6));// 部门
                            candidate.setTitle(object.get(7));// 职位
                            candidateRepository.save(candidate);
                        }
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext context) {
                    }
                });
                reader.read();
                // 关闭流文件
                in.close();
            }
        } catch (Exception ex) {
            log.error("{}", ex);
        }
        return result;
    }
}
