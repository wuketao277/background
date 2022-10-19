package com.hello.background.service;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSONObject;
import com.hello.background.common.CommonUtils;
import com.hello.background.domain.Candidate;
import com.hello.background.domain.CandidateAttention;
import com.hello.background.repository.CandidateAttentionRepository;
import com.hello.background.repository.CandidateRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.CandidateAttentionVO;
import com.hello.background.vo.CandidateVO;
import com.hello.background.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;
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
    @Autowired
    private CandidateAttentionRepository candidateAttentionRepository;

    private ThreadLocal<Integer> tlRowNumber = new ThreadLocal<>();

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
            return TransferUtil.transferTo(CommonUtils.calcAge(candidate), CandidateVO.class);
        }
        return null;
    }

    /**
     * 保存候选人
     *
     * @param vo
     * @return
     */
    public CandidateVO save(CandidateVO vo, UserVO user) {
        Candidate candidate = TransferUtil.transferTo(vo, Candidate.class);
        // 如果没有id，表示是第一次新增。就增加创建用户。
        if (null == candidate.getId() || null == candidate.getCreateUserId()) {
            candidate.setCreateUserId(user.getId());
            candidate.setCreateUserName(user.getUsername());
            candidate.setCreateRealName(user.getRealname());
        }
        candidate = candidateRepository.save(candidate);
        return TransferUtil.transferTo(CommonUtils.calcAge(candidate), CandidateVO.class);
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
        List<String> searchWordList = CommonUtils.splitSearchWord(search);
        Specification<Candidate> specification = new Specification<Candidate>() {
            @Override
            public Predicate toPredicate(Root<Candidate> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                for (String searchWord : searchWordList) {
                    list.add(criteriaBuilder.and(criteriaBuilder.or(
                            getPredicate("chineseName", searchWord, root, criteriaBuilder)
                            , getPredicate("englishName", searchWord, root, criteriaBuilder)
                            , getPredicate("phoneNo", searchWord, root, criteriaBuilder)
                            , getPredicate("email", searchWord, root, criteriaBuilder)
                            , getPredicate("companyName", searchWord, root, criteriaBuilder)
                            , getPredicate("department", searchWord, root, criteriaBuilder)
                            , getPredicate("title", searchWord, root, criteriaBuilder)
                            , getPredicate("schoolName", searchWord, root, criteriaBuilder)
                            , getPredicate("currentAddress", searchWord, root, criteriaBuilder)
                            , getPredicate("futureAddress", searchWord, root, criteriaBuilder)
                            , getPredicate("remark", searchWord, root, criteriaBuilder)
                            , getPredicate("createUserName", searchWord, root, criteriaBuilder)
                            , getPredicate("createRealName", searchWord, root, criteriaBuilder)
                    )));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        };
        Page<Candidate> all = candidateRepository.findAll(specification, pageable);
        Page<CandidateVO> map = all.map(x -> TransferUtil.transferTo(CommonUtils.calcAge(x), CandidateVO.class));
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()),
                all.getTotalElements());
        return map;
    }

    /**
     * 获取查询条件中的谓词
     *
     * @param key
     * @param root
     * @param criteriaBuilder
     * @return
     */
    private Predicate getPredicate(String key, String value, Root<Candidate> root, CriteriaBuilder criteriaBuilder) {
        Path<String> path = root.get(key);
        Predicate predicate = criteriaBuilder.like(path, "%" + value + "%");
        return predicate;
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
        return candidateList.stream().map(x -> TransferUtil.transferTo(CommonUtils.calcAge(x), CandidateVO.class)).collect(Collectors.toList());
    }

    /**
     * 获取所有候选人
     *
     * @return
     */
    public List<CandidateVO> findAll() {
        Specification<Candidate> specification = new Specification<Candidate>() {
            @Override
            public Predicate toPredicate(Root<Candidate> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };
        List<Candidate> all = candidateRepository.findAll(specification);
        return all.stream().map(x -> TransferUtil.transferTo(CommonUtils.calcAge(x), CandidateVO.class)).collect(Collectors.toList());
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
                        saveCandidate(object, result);
                        // 行号+1
                        tlRowNumber.set(tlRowNumber.get() + 1);
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
            result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行列数量错误。\r\n");
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
                    result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行的'日期'列内容超长。\r\n");
                } else if (object.get(1).length() > 25) {
                    tempFlag = false;
                    result.put("flag", false);
                    result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行" + object.get(1) + "的'名字'列内容超长。\r\n");
                } else if (object.get(2).length() > 50) {
                    tempFlag = false;
                    result.put("flag", false);
                    result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行" + object.get(1) + "的'英文名'列内容超长。\r\n");
                } else if (object.get(3).length() > 20) {
                    tempFlag = false;
                    result.put("flag", false);
                    result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行" + object.get(1) + "的'生日'列内容超长。\r\n");
                } else if (object.get(4).length() > 20) {
                    tempFlag = false;
                    result.put("flag", false);
                    result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行" + object.get(1) + "的'手机号'列内容超长。\r\n");
                } else if (object.get(5).length() > 200) {
                    tempFlag = false;
                    result.put("flag", false);
                    result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行" + object.get(1) + "的'邮箱'列内容超长。\r\n");
                } else if (object.get(6).length() > 200) {
                    tempFlag = false;
                    result.put("flag", false);
                    result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行" + object.get(1) + "的'公司'列内容超长。\r\n");
                } else if (object.get(7).length() > 200) {
                    tempFlag = false;
                    result.put("flag", false);
                    result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行" + object.get(1) + "的'部门'列内容超长。\r\n");
                } else if (object.get(8).length() > 200) {
                    tempFlag = false;
                    result.put("flag", false);
                    result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行" + object.get(1) + "的'职位'列内容超长。\r\n");
                } else if (object.get(9).length() > 100) {
                    tempFlag = false;
                    result.put("flag", false);
                    result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行" + object.get(1) + "的'学校'列内容超长。\r\n");
                } else if (object.get(10).length() > 100) {
                    tempFlag = false;
                    result.put("flag", false);
                    result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行" + object.get(1) + "的'现地'列内容超长。\r\n");
                } else if (object.get(11).length() > 100) {
                    tempFlag = false;
                    result.put("flag", false);
                    result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行" + object.get(1) + "的'期地'列内容超长。\r\n");
                } else if (object.get(12).length() > 100) {
                    tempFlag = false;
                    result.put("flag", false);
                    result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行" + object.get(1) + "的'薪资'列内容超长。\r\n");
                } else if (object.get(13).length() > 100) {
                    tempFlag = false;
                    result.put("flag", false);
                    result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行" + object.get(1) + "的'期薪'列内容超长。\r\n");
                } else if (object.get(14).length() > 1000) {
                    tempFlag = false;
                    result.put("flag", false);
                    result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行" + object.get(1) + "的'备注'列内容超长。\r\n");
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
                result.put("msg", result.get("msg") + "第" + tlRowNumber.get() + "行" + object.get(1) + "的电话号码已存在。\r\n");
            }
        }
    }

    /**
     * 通过电话号码查询候选人列表
     *
     * @param phoneNo
     * @return
     */
    public List<CandidateVO> findByPhoneNo(String phoneNo) {
        List<Candidate> list = candidateRepository.findByPhoneNo(phoneNo);
        return list.stream().map(x -> TransferUtil.transferTo(CommonUtils.calcAge(x), CandidateVO.class)).collect(Collectors.toList());
    }

    /**
     * 更新候选人关注信息
     *
     * @param attention
     * @param candidateId
     * @param userVO
     */
    public void updateCandidateAttention(Boolean attention, Integer candidateId, UserVO userVO) {
        if (attention) {
            // 要添加关注
            // 先检查是否已关注
            List<CandidateAttention> candidateAttentionList = candidateAttentionRepository.findByCandidateIdAndUserId(candidateId, userVO.getId());
            if (0 == candidateAttentionList.size()) {
                // 如果没有关注，就增加关注
                Optional<Candidate> optional = candidateRepository.findById(candidateId);
                if (optional.isPresent()) {
                    Candidate candidate = optional.get();
                    CandidateAttention candidateAttention = new CandidateAttention();
                    candidateAttention.setCandidateId(candidate.getId());
                    candidateAttention.setCandidateChineseName(candidate.getChineseName());
                    candidateAttention.setUserId(userVO.getId());
                    candidateAttention.setUserLoginName(userVO.getUsername());
                    candidateAttention.setUserRealName(userVO.getRealname());
                    candidateAttentionRepository.save(candidateAttention);
                }
            }
        } else {
            // 取消关注
            candidateAttentionRepository.deleteByCandidateIdAndUserId(candidateId, userVO.getId());
        }
    }

    /**
     * 查询候选人关注情况
     *
     * @param candidateId
     * @param userVO
     * @return
     */
    public Boolean queryCandidateAttentionByCandidateId(Integer candidateId, UserVO userVO) {
        List<CandidateAttention> candidateAttentionList = candidateAttentionRepository.findByCandidateIdAndUserId(candidateId, userVO.getId());
        return candidateAttentionList.size() > 0;
    }

    /**
     * 通过用户的关注情况
     *
     * @param userId
     * @return
     */
    public List<CandidateAttentionVO> queryCandidateAttentionListByUser(Integer userId) {
        List<CandidateAttention> candidateAttentionList = candidateAttentionRepository.findByUserId(userId);
        return candidateAttentionList.stream().map(x -> TransferUtil.transferTo(x, CandidateAttentionVO.class)).collect(Collectors.toList());
    }
}
