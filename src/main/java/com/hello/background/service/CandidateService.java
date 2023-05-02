package com.hello.background.service;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSONObject;
import com.hello.background.common.CommonUtils;
import com.hello.background.constant.SchoolConstant;
import com.hello.background.domain.Candidate;
import com.hello.background.domain.CandidateAttention;
import com.hello.background.domain.CandidateForCase;
import com.hello.background.repository.CandidateAttentionRepository;
import com.hello.background.repository.CandidateForCaseRepository;
import com.hello.background.repository.CandidateRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.CandidateAttentionVO;
import com.hello.background.vo.CandidateVO;
import com.hello.background.vo.SearchCandidateListCondition;
import com.hello.background.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
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
import java.util.stream.Stream;

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
    @Autowired
    private CandidateForCaseRepository candidateForCaseRepository;

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
            return CandidateVO.fromCandidate(candidate);
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
        Candidate candidate = vo.toCandidate();
        // 如果没有id，表示是第一次新增。就增加创建用户。
        if (null == candidate.getId() || null == candidate.getCreateUserId()) {
            candidate.setCreateUserId(user.getId());
            candidate.setCreateUserName(user.getUsername());
            candidate.setCreateRealName(user.getRealname());
        } else {
            // 对于已经存在的候选人
            // 更新关注候选人
            List<CandidateAttention> candidateAttentionList = candidateAttentionRepository.findByCandidateId(vo.getId());
            candidateAttentionList.forEach(ca -> {
                if (Strings.isNotBlank(ca.getCandidateChineseName()) && !ca.getCandidateChineseName().equals(vo.getChineseName())) {
                    ca.setCandidateChineseName(vo.getChineseName());
                    candidateAttentionRepository.save(ca);
                }
            });
            // 更新候选人-职位关系中的缓存数据
            List<CandidateForCase> candidateForCaseList = candidateForCaseRepository.findByCandidateId(candidate.getId());
            candidateForCaseList.forEach(cfc -> {
                if ((Strings.isNotBlank(vo.getChineseName()) && (Strings.isBlank(cfc.getChineseName()) || !vo.getChineseName().equals(cfc.getChineseName())))
                        || (Strings.isNotBlank(vo.getEnglishName()) && (Strings.isBlank(cfc.getEnglishName()) || !vo.getEnglishName().equals(cfc.getEnglishName())))) {
                    cfc.setChineseName(vo.getChineseName());
                    cfc.setEnglishName(vo.getEnglishName());
                    candidateForCaseRepository.save(cfc);
                }
            });
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
        Page<CandidateVO> map = all.map(x -> CandidateVO.fromCandidate(x));
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
        return candidateList.stream().map(x -> CandidateVO.fromCandidate(x)).collect(Collectors.toList());
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
        return all.stream().map(x -> CandidateVO.fromCandidate(x)).collect(Collectors.toList());
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
        return list.stream().map(x -> CandidateVO.fromCandidate(x)).collect(Collectors.toList());
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
        return candidateAttentionList.parallelStream().map(x -> {
            CandidateAttentionVO vo = TransferUtil.transferTo(x, CandidateAttentionVO.class);
            Optional<Candidate> optional = candidateRepository.findById(vo.getCandidateId());
            if (optional.isPresent()) {
                if (Strings.isNotBlank(optional.get().getLabels())) {
                    vo.setLabels(Arrays.asList(optional.get().getLabels().split(",")));
                }
            }
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 搜索候选人集合
     *
     * @param condition
     * @return
     */
    public List<CandidateVO> searchCandidateList(SearchCandidateListCondition condition) {
        List<String> searchWordList = CommonUtils.splitSearchWord(condition.getKeyWords());
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
                if (null != condition.getGender()) {
                    list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("gender"), condition.getGender())));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        };
        // 通过条件查询数据，获取流对象
        Stream<Candidate> stream = candidateRepository.findAll(specification).parallelStream();
        // 检查学校
        if (null != condition.getSchoolLevel() && condition.getSchoolLevel().size() > 0) {
            boolean is985 = condition.getSchoolLevel().contains("985");
            boolean is211 = condition.getSchoolLevel().contains("211");
            boolean is11 = condition.getSchoolLevel().contains("双一流");
            stream = stream.filter(c -> checkCandidateSchool(c, is985, is211, is11));
        }
        // 检查年龄
        if (null != condition.getAgeMin() || null != condition.getAgeMax()) {
            stream = stream.filter(c -> checkAge(c, condition.getAgeMin(), condition.getAgeMax()));
        }
        // 检查最远阶段
        if (Strings.isNotBlank(condition.getFarthestPhase()) && !condition.getFarthestPhase().equals("无")) {
            stream = stream.filter(c -> checkFarthestPhase(c, conditionConvertForFarthestPhase(condition.getFarthestPhase())));
        }
        // 数据转换
        List<CandidateVO> voList = stream.map(x -> CandidateVO.fromCandidate(x)).collect(Collectors.toList());
        return voList;
    }

    /**
     * 检查年龄
     *
     * @param candidate
     * @param ageMin
     * @param ageMax
     * @return
     */
    public boolean checkAge(Candidate candidate, Integer ageMin, Integer ageMax) {
        Integer age = CommonUtils.calcAge(candidate.getBirthDay());
        if (null == age) {
            return true;
        }
        if (null != ageMin && ageMin > age) {
            return false;
        }
        if (null != ageMax && ageMax < age) {
            return false;
        }
        return true;
    }

    /**
     * 条件转换
     *
     * @param phase
     * @return
     */
    public String conditionConvertForFarthestPhase(String phase) {
        switch (phase) {
            case "CVO":
                return "CVO";
            case "interview1":
                return "1st Interview";
            case "interview2":
                return "2nd Interview";
            case "interview3":
                return "3rd Interview";
            case "interview4":
                return "4th Interview";
            case "finalInterview":
                return "Final Interview";
            case "offerSigned":
                return "Offer Signed";
            case "onBoard":
                return "On Board";
        }
        return "";
    }

    /**
     * 检查学校是否是985 211 双一流
     *
     * @param candidate
     * @param is985     检查985
     * @param is211     检查211
     * @param is11      检查双一流
     * @return
     */
    private boolean checkCandidateSchool(Candidate candidate, boolean is985, boolean is211, boolean is11) {
        if (null == candidate || Strings.isBlank(candidate.getSchoolName())) {
            return false;
        } else {
            String school = candidate.getSchoolName();
            if (is985) {
                for (String s : SchoolConstant.s985) {
                    if (school.indexOf(s) > -1) {
                        return true;
                    }
                }
            }
            if (is211) {
                for (String s : SchoolConstant.s211) {
                    if (school.indexOf(s) > -1) {
                        return true;
                    }
                }
            }
            if (is11) {
                for (String s : SchoolConstant.sShuangyiliu) {
                    if (school.indexOf(s) > -1) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    /**
     * 检查最远阶段
     *
     * @param candidate
     * @param farthestPhase
     * @return
     */
    private boolean checkFarthestPhase(Candidate candidate, String farthestPhase) {
        List<CandidateForCase> candidateForCaseList = candidateForCaseRepository.findByCandidateId(candidate.getId());
        for (CandidateForCase cc : candidateForCaseList) {
            if (compareFarthestPhase(cc.getFarthestPhase(), farthestPhase)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 比较最远阶段
     *
     * @param a
     * @param b
     * @return
     */
    private boolean compareFarthestPhase(String a, String b) {
        if (Strings.isBlank(a) || Strings.isBlank(b)) {
            return false;
        }
        return transformFarthestPhaseToInt(a) >= transformFarthestPhaseToInt(b);
    }

    /**
     * 将最远阶段转换为数字
     *
     * @param phase
     * @return
     */
    private int transformFarthestPhaseToInt(String phase) {
        switch (phase) {
            case "CVO":
                return 0;
            case "1st Interview":
                return 1;
            case "2nd Interview":
                return 2;
            case "3rd Interview":
                return 3;
            case "4th Interview":
                return 4;
            case "Final Interview":
                return 5;
            case "Offer Signed":
                return 6;
            case "On Board":
                return 7;
            case "Invoice":
                return 8;
            case "Payment":
                return 9;
            case "Successful":
                return 10;
        }
        return -1;
    }
}
