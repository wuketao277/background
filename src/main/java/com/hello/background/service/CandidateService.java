package com.hello.background.service;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.hello.background.domain.Candidate;
import com.hello.background.repository.CandidateRepository;
import com.hello.background.utils.DateTimeUtil;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.CandidateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wuketao
 * @date 2019/12/14
 * @Description
 */
@Transactional
@Slf4j
@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private ResumeService resumeService;
    @Autowired
    private CommentService commentService;

    private ThreadLocal<Integer> tlRowNumber = new ThreadLocal<>();

    /**
     * 计算年龄
     *
     * @param candidate
     * @return
     */
    private Candidate calcAge(Candidate candidate) {
        if (!StringUtils.isEmpty(candidate.getBirthDay())) {
            try {
                LocalDate ld = DateTimeUtil.convertToLocalDate(candidate.getBirthDay());
                if (null == ld) {
                    return candidate;
                }
                LocalDate now = LocalDate.now();
                int year = now.getYear() - ld.getYear();
                int month = now.getMonthValue() - ld.getMonthValue();
                candidate.setAge(month > 0 ? year + 1 : year);
            } catch (Exception ex) {
            }
        }
        return candidate;
    }

    /**
     * 通过id，查询候选人信息
     *
     * @param id 候选人主键
     * @return
     */
    public CandidateVO findById(Integer id) {
        Optional<Candidate> candidateOptional = candidateRepository.findById(id);
        if (candidateOptional.isPresent()) {
            Candidate candidate = candidateOptional.get();
            return TransferUtil.transferTo(calcAge(candidate), CandidateVO.class);
        }
        return null;
    }

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
        Pageable pageable = new PageRequest(currentPage - 1, pageSize, Sort.Direction.DESC, "id");
        Page<Candidate> cadidatePage = null;
        long total = 0;
        if (Strings.isNullOrEmpty(search)) {
            cadidatePage = candidateRepository.findAll(pageable);
            total = candidateRepository.count();
        } else {
            cadidatePage = candidateRepository.findByChineseNameLikeOrEnglishNameLikeOrPhoneNoLikeOrEmailLikeOrCompanyNameLikeOrDepartmentLikeOrTitleLikeOrSchoolNameLikeOrCurrentAddressLikeOrFutureAddressLikeOrRemarkLikeOrderByIdDesc(search, search, search, search, search, search, search, search, search, search, search, pageable);
            total = candidateRepository.countByChineseNameLikeOrEnglishNameLikeOrPhoneNoLikeOrEmailLikeOrCompanyNameLikeOrDepartmentLikeOrTitleLikeOrSchoolNameLikeOrCurrentAddressLikeOrFutureAddressLikeOrRemarkLike(search, search, search, search, search, search, search, search, search, search, search);
        }
        Page<CandidateVO> map = cadidatePage.map(x -> TransferUtil.transferTo(calcAge(x), CandidateVO.class));
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()),
                total);
        return map;
    }

    /**
     * 查询分页
     *
     * @param search 搜索关键字
     * @return
     */
    public List<CandidateVO> queryCandidate(String search) {
        // 首先搜索候选人信息
        List<Candidate> candidateList = candidateRepository.findByChineseNameLikeOrEnglishNameLikeOrPhoneNoLikeOrEmailLikeOrCompanyNameLikeOrDepartmentLikeOrTitleLikeOrSchoolNameLikeOrCurrentAddressLikeOrFutureAddressLikeOrRemarkLikeOrderByIdDesc(search, search, search, search, search, search, search, search, search, search, search);
        List<Integer> candidateIdList = candidateList.stream().map(x -> x.getId()).collect(Collectors.toList());
        // 在简历中搜索关键字
        Set<Integer> resumeCandidateIdSet = resumeService.findByContentLikeOrderByCandidateIdAscIdAsc(search).stream().map(x -> x.getCandidateId()).collect(Collectors.toSet());
        // 删除重复的候选人信息
        resumeCandidateIdSet.removeIf(x -> candidateIdList.contains(x));
        // 添加新的候选人信息
        candidateIdList.addAll(resumeCandidateIdSet);
        for (Integer id : resumeCandidateIdSet) {
            Optional<Candidate> candidateOptional = candidateRepository.findById(id);
            if (candidateOptional.isPresent()) {
                candidateList.add(candidateOptional.get());
            }
        }
        // 在评论中搜索关键字
        Set<Integer> commentCandidateIdSet = commentService.findByContentLikeOrderByCandidateIdAscIdAsc(search).stream().map(x -> x.getCandidateId()).collect(Collectors.toSet());
        // 删除重复的候选人信息
        commentCandidateIdSet.removeIf(x -> candidateIdList.contains(x));
        // 添加新的候选人信息
        candidateIdList.addAll(commentCandidateIdSet);
        for (Integer id : commentCandidateIdSet) {
            Optional<Candidate> candidateOptional = candidateRepository.findById(id);
            if (candidateOptional.isPresent()) {
                candidateList.add(candidateOptional.get());
            }
        }
        return candidateList.stream().map(x -> TransferUtil.transferTo(calcAge(x), CandidateVO.class)).collect(Collectors.toList());
    }

    /**
     * 获取所有新闻
     *
     * @return
     */
    public List<CandidateVO> findAll() {
        List<Candidate> all = candidateRepository.findAll();
        return all.stream().map(x -> TransferUtil.transferTo(calcAge(x), CandidateVO.class)).collect(Collectors.toList());
    }

    /**
     * 分析上传文件
     *
     * @param request
     * @return
     */
    public JSONObject analysisUploadFile(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        result.put("flag", true);
        result.put("msg", "");
        try {
            // 设置行号从2开始
            tlRowNumber.set(2);
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            if (fileMap.isEmpty()) {
                result.put("flag", false);
                result.put("msg", "上传文件为空");
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
                        if ((boolean) result.get("flag")) {
                            saveCandidate(object, result);
                            // 行号+1
                            tlRowNumber.set(tlRowNumber.get() + 1);
                        }
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext context) {
                    }
                });
                reader.read();
                // 关闭流文件
                in.close();
                break;
            }
        } catch (Exception ex) {
            log.error("{}", ex);
        } finally {
            if (!(boolean) result.get("flag")) {
                result.put("msg", result.get("msg") + "之后的数据都没能成功导入。");
            }
            // 删除ThreadLocal中的行号
            tlRowNumber.remove();
        }
        return result;
    }

    /**
     * 获取候选人对象
     *
     * @param object
     * @param result
     * @return
     */
    private void saveCandidate(List<String> object, JSONObject result) {
        Field[] declaredFields = Candidate.class.getDeclaredFields();
        if (declaredFields.length - 2 != object.size()) {
            result.put("flag", false);
            result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行列数量错误。");
        } else {
            // 相同手机号的不能导入
            // 电话
            List<Candidate> oldCandidateList = candidateRepository.findByPhoneNo(object.get(4));
            if (CollectionUtils.isEmpty(oldCandidateList)) {
                Candidate candidate = new Candidate();
                boolean tempFlag = true;
                if (object.get(0).length() > 20) {
                    tempFlag = false;
                    result.put("flag", false);
                    result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行的'日期'列内容超长。");
                } else if (object.get(1).length() > 25) {
                    tempFlag = false;
                    result.put("flag", false);
                    result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行" + object.get(1) + "的'名字'列内容超长。");
                } else if (object.get(2).length() > 50) {
                    tempFlag = false;
                    result.put("flag", false);
                    result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行" + object.get(1) + "的'英文名'列内容超长。");
                } else if (object.get(3).length() > 20) {
                    tempFlag = false;
                    result.put("flag", false);
                    result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行" + object.get(1) + "的'生日'列内容超长。");
                } else if (object.get(4).length() > 20) {
                    tempFlag = false;
                    result.put("flag", false);
                    result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行" + object.get(1) + "的'手机号'列内容超长。");
                } else if (object.get(5).length() > 200) {
                    tempFlag = false;
                    result.put("flag", false);
                    result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行" + object.get(1) + "的'邮箱'列内容超长。");
                } else if (object.get(6).length() > 200) {
                    tempFlag = false;
                    result.put("flag", false);
                    result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行" + object.get(1) + "的'公司'列内容超长。");
                } else if (object.get(7).length() > 200) {
                    tempFlag = false;
                    result.put("flag", false);
                    result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行" + object.get(1) + "的'部门'列内容超长。");
                } else if (object.get(8).length() > 200) {
                    tempFlag = false;
                    result.put("flag", false);
                    result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行" + object.get(1) + "的'职位'列内容超长。");
                } else if (object.get(9).length() > 100) {
                    tempFlag = false;
                    result.put("flag", false);
                    result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行" + object.get(1) + "的'学校'列内容超长。");
                } else if (object.get(10).length() > 100) {
                    tempFlag = false;
                    result.put("flag", false);
                    result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行" + object.get(1) + "的'现地'列内容超长。");
                } else if (object.get(11).length() > 100) {
                    tempFlag = false;
                    result.put("flag", false);
                    result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行" + object.get(1) + "的'期地'列内容超长。");
                } else if (object.get(12).length() > 100) {
                    tempFlag = false;
                    result.put("flag", false);
                    result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行" + object.get(1) + "的'薪资'列内容超长。");
                } else if (object.get(13).length() > 100) {
                    tempFlag = false;
                    result.put("flag", false);
                    result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行" + object.get(1) + "的'期薪'列内容超长。");
                } else if (object.get(14).length() > 1000) {
                    tempFlag = false;
                    result.put("flag", false);
                    result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行" + object.get(1) + "的'备注'列内容超长。");
                }
                if (tempFlag) {
                    //日期
                    candidate.setDate(object.get(0));
                    //中文名字
                    candidate.setChineseName(object.get(1));
                    //英文名字
                    candidate.setEnglishName(object.get(2));
                    // 生日
                    if (!StringUtils.isEmpty(object.get(3))) {
                        candidate.setBirthDay(object.get(3));
                    }
                    // 电话
                    candidate.setPhoneNo(object.get(4));
                    // 邮箱
                    candidate.setEmail(object.get(5));
                    // 公司
                    candidate.setCompanyName(object.get(6));
                    // 部门
                    candidate.setDepartment(object.get(7));
                    // 职位
                    candidate.setTitle(object.get(8));
                    // 学校名称
                    candidate.setSchoolName(object.get(9));
                    // 现地址
                    candidate.setCurrentAddress(object.get(10));
                    // 期望地址
                    candidate.setFutureAddress(object.get(11));
                    // 现薪资
                    candidate.setCurrentMoney(object.get(12));
                    // 期望薪资
                    candidate.setFutureMoney(object.get(13));
                    // 备注
                    candidate.setRemark(object.get(14));
                    candidateRepository.save(candidate);
                }
            } else {
                result.put("flag", false);
                result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行" + object.get(1) + "的电话号码已存在。");
            }
        }
    }
}
