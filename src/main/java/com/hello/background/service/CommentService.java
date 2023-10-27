package com.hello.background.service;

import com.hello.background.common.CommonUtils;
import com.hello.background.constant.KPIStandardConstants;
import com.hello.background.constant.RoleEnum;
import com.hello.background.domain.Candidate;
import com.hello.background.domain.ClientCase;
import com.hello.background.domain.Comment;
import com.hello.background.domain.KPI;
import com.hello.background.repository.CandidateRepository;
import com.hello.background.repository.CaseRepository;
import com.hello.background.repository.CommentRepository;
import com.hello.background.utils.EasyExcelUtil;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wuketao
 * @date 2019/12/22
 * @Description
 */
@Slf4j
@Transactional
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CaseRepository caseRepository;
    @Autowired
    private CommonService commonService;
    @Autowired
    private KPIService kpiService;

    /**
     * 通过id删除
     *
     * @param id
     */
    public void deleteById(Integer id) {
        commentRepository.deleteById(id);
    }

    /**
     * 保存评论信息
     *
     * @param vo
     * @return
     */
    public CommentVO save(CommentVO vo) {
        Comment comment = TransferUtil.transferTo(vo, Comment.class);
        Comment resultComment = commentRepository.save(comment);
        return TransferUtil.transferTo(resultComment, CommentVO.class);
    }

    /**
     * 通过候选人ID查询所有评论
     *
     * @param candidateId
     * @return
     */
    public List<CommentVO> findAllByCandidateId(Integer candidateId) {
        List<Comment> allByCandidateId = commentRepository.findAllByCandidateId(candidateId);
        return allByCandidateId.stream().map(comment -> TransferUtil.transferTo(comment, CommentVO.class)).collect(Collectors.toList());
    }

    /**
     * 通过候选人ID查询所有评论
     *
     * @param candidateId
     * @return
     */
    public List<CommentVO> findAllByCandidateIdAndUsername(Integer candidateId, String username) {
        List<Comment> allByCandidateId = commentRepository.findAllByCandidateIdAndUsername(candidateId, username);
        return allByCandidateId.stream().map(comment -> TransferUtil.transferTo(comment, CommentVO.class)).collect(Collectors.toList());
    }

    /**
     * 通过候选人ID查询所有评论
     *
     * @param candidateId
     * @return
     */
    public List<CommentVO> findAllByCandidateIdOrderByDesc(Integer candidateId) {
        List<Comment> allByCandidateId = commentRepository.findAllByCandidateIdOrderByInputTimeDesc(candidateId);
        return allByCandidateId.stream().map(comment -> TransferUtil.transferTo(comment, CommentVO.class)).collect(Collectors.toList());
    }

    /**
     * 通过评论内容查询评论
     *
     * @param content
     * @return
     */
    public List<Comment> findByContentLikeOrderByCandidateIdAscIdAsc(String content) {
        return commentRepository.findByContentLikeOrderByCandidateIdAscIdAsc(content);
    }

    /**
     * 保存KPI
     *
     * @param monthLD
     */
    public void saveKPI(LocalDate monthLD) {
        // 转换日期字符串格式
        String month = monthLD.format(DateTimeFormatter.ofPattern("YYYY-MM"));
        // 删除旧数据
        kpiService.deleteByMonth(month);
        // 计算新的KPI
        List<KPIPerson> kpiPersonList = calcKPI(monthLD, monthLD.plusMonths(1).plusDays(-1), "all", null, false);
        List<UserVO> userVOList = userService.findByEnabled(true);
        // 保存进数据库
        kpiPersonList.forEach(k -> {
            KPI kpi = new KPI();
            kpi.setMonth(month);
            // 获取用户当月是否考核KPI
            Optional<UserVO> userOp = userVOList.stream().filter(u -> u.getUsername().equals(k.getUserName())).findFirst();
            boolean checkKPI = userOp.isPresent() ? userOp.get().getCheckKPI() : false;
            kpi.setCheckKPI(checkKPI);
            TransferUtil.transfer(k, kpi);
            kpiService.save(kpi);
        });
    }

    /**
     * 计算KPI
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return
     */
    public List<KPIPerson> calcKPI(LocalDate start, LocalDate end, String scope, UserVO userVO, boolean kpiOnlyShowCheck) {
        // 获取计算时间段
        LocalDateTime startDT = LocalDateTime.of(start.getYear(), start.getMonthValue(), start.getDayOfMonth(), 0, 0, 0);
        LocalDateTime endDT = LocalDateTime.of(end.getYear(), end.getMonthValue(), end.getDayOfMonth(), 23, 59, 59);
        // 通过Scope获取用户集合
        List<UserVO> userVOList = userService.findByScope(scope, userVO);
        // 判断是否只检查考核KPI人员的数据
        if (kpiOnlyShowCheck) {
            userVOList = userVOList.stream().filter(u -> u.getCheckKPI()).collect(Collectors.toList());
        }
        // 获取该时间段内指定用户所有评论
        List<Comment> commentList = commentRepository.findByInputTimeBetweenAndUsernameIn(startDT, endDT, userVOList.stream().map(u -> u.getUsername()).collect(Collectors.toList()));
        // 创建一个kpi对象集合
        List<KPIPerson> kpiPersonList = new ArrayList<>();
        // 遍历评论，计算kpi
        for (Comment cnt : commentList) {
            KPIPerson kpiPerson = new KPIPerson();
            Optional<KPIPerson> first = kpiPersonList.stream().filter(kpi -> kpi.getUserName().equals(cnt.getUsername())).findFirst();
            if (first.isPresent()) {
                kpiPerson = first.get();
            } else {
                kpiPerson.setRealName(cnt.getRealname());
                kpiPerson.setUserName(cnt.getUsername());
                kpiPerson.setUserId(userVOList.stream().filter(u -> u.getUsername().equals(cnt.getUsername())).findFirst().get().getId());
                kpiPersonList.add(kpiPerson);
            }
            switch (cnt.getPhase()) {
                case "TI":
                    kpiPerson.setTi(kpiPerson.getTi() + 1);
                    kpiPerson.setTicf(kpiPerson.getTicf() + 1);
                    break;
                case "CF":
                    kpiPerson.setTicf(kpiPerson.getTicf() + 1);
                    break;
                case "VI":
                    kpiPerson.setVi(kpiPerson.getVi() + 1);
                    kpiPerson.setViioi(kpiPerson.getViioi() + 1);
                    break;
                case "IOI":
                    kpiPerson.setIoi(kpiPerson.getIoi() + 1);
                    kpiPerson.setViioi(kpiPerson.getViioi() + 1);
                    break;
                case "CVO":
                    kpiPerson.setCvo(kpiPerson.getCvo() + 1);
                    break;
                case "1st Interview":
                    kpiPerson.setInterview1st(kpiPerson.getInterview1st() + 1);
                    break;
                case "2nd Interview":
                    kpiPerson.setInterview2nd(kpiPerson.getInterview2nd() + 1);
                    break;
                case "3rd Interview":
                    kpiPerson.setInterview3rd(kpiPerson.getInterview3rd() + 1);
                    break;
                case "4th Interview":
                    kpiPerson.setInterview4th(kpiPerson.getInterview4th() + 1);
                    break;
                case "5th Interview":
                    kpiPerson.setInterview5th(kpiPerson.getInterview5th() + 1);
                    break;
                case "6th Interview":
                    kpiPerson.setInterview6th(kpiPerson.getInterview6th() + 1);
                    break;
                case "Final Interview":
                    kpiPerson.setInterviewFinal(kpiPerson.getInterviewFinal() + 1);
                    break;
                case "Offer Signed":
                    kpiPerson.setOfferSigned(kpiPerson.getOfferSigned() + 1);
                    break;
                case "On Board":
                    kpiPerson.setOnBoard(kpiPerson.getOnBoard() + 1);
                    break;
            }
        }
        // 计算KPI得分
        kpiPersonList.forEach(k -> calcKPIFinishRate(k, start, end));
        // 按照KPI得分排序
        kpiPersonList.sort(Comparator.comparing(KPIPerson::getFinishRate));

        // 统计新增的候选人数据
        List<Candidate> newCandidateList = candidateRepository.findByCreateTimeGreaterThanEqualAndCreateTimeLessThanEqual(Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(startDT), Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(endDT));
        kpiPersonList.forEach(k -> {
            k.setNewCandidates(
                    newCandidateList.stream().filter(c -> Strings.isNotBlank(c.getCreateUserName()) && c.getCreateUserName().equals(k.getUserName())).collect(Collectors.toList()).size());
        });
        return kpiPersonList;
    }

    /**
     * 计算KPI中的完成比例
     *
     * @param kpiPerson
     */
    private void calcKPIFinishRate(KPIPerson kpiPerson, LocalDate start, LocalDate end) {
        // 计算顾问本月工作日
        BigDecimal workingDays = commonService.calcWorkdaysBetween(start, end, kpiPerson.getUserName());
        if (BigDecimal.ZERO.compareTo(workingDays) >= 0) {
            return;
        }
        kpiPerson.setWorkDays(workingDays);
        // 读取顾问是AM还是Recruiter
        UserVO userVO = userService.findById(kpiPerson.getUserId());
        boolean isAM = userVO.getRoles().stream().anyMatch(r -> r.equals(RoleEnum.AM));
        boolean isRecuriter = userVO.getRoles().stream().anyMatch(r -> r.equals(RoleEnum.RECRUITER));
        // 读取各项指标数据，计算得分
        if (isAM) {
            //  计算AM的KPI完成比例
            BigDecimal viioiPercent = calcKPIPercent(kpiPerson.getViioi(), KPIStandardConstants.amVIIOICount, KPIStandardConstants.amVIIOIPercent, workingDays, true);
            kpiPerson.setFinishRateVIIOI(viioiPercent.multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP));
            BigDecimal cvoPercent = calcKPIPercent(kpiPerson.getCvo(), KPIStandardConstants.amCVOCount, KPIStandardConstants.amCVOPercent, workingDays, true);
            kpiPerson.setFinishRateCVO(cvoPercent.multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP));
            BigDecimal interviewPercent = calcKPIPercentForInterview(kpiPerson, true, workingDays);
            kpiPerson.setFinishRateInterview(interviewPercent.multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP));
            kpiPerson.setFinishRate(viioiPercent.add(cvoPercent).add(interviewPercent).multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP));
        } else {
            // 计算Recruiter的KPI完成比例
            BigDecimal ticfPercent = calcKPIPercent(kpiPerson.getTicf(), KPIStandardConstants.reTICFCount, KPIStandardConstants.reTICFPercent, workingDays, false);
            kpiPerson.setFinishRateTICF(ticfPercent.multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP));
            BigDecimal viioiPercent = calcKPIPercent(kpiPerson.getViioi(), KPIStandardConstants.reVIIOICount, KPIStandardConstants.reVIIOIPercent, workingDays, true);
            kpiPerson.setFinishRateVIIOI(viioiPercent.multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP));
            BigDecimal cvoPercent = calcKPIPercent(kpiPerson.getCvo(), KPIStandardConstants.reCVOCount, KPIStandardConstants.reCVOPercent, workingDays, true);
            kpiPerson.setFinishRateCVO(cvoPercent.multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP));
            BigDecimal interviewPercent = calcKPIPercentForInterview(kpiPerson, false, workingDays);
            kpiPerson.setFinishRateInterview(interviewPercent.multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP));
            // Recruiter的ti+vi总和不能超过0.2
            BigDecimal tivi = ticfPercent.add(viioiPercent).compareTo(new BigDecimal(0.2)) > 0 ? new BigDecimal(0.2) : ticfPercent.add(viioiPercent);
            kpiPerson.setFinishRate(tivi.add(cvoPercent).add(interviewPercent).multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP));
        }
    }

    /**
     * 计算KPI各考核项的占比得分
     *
     * @param actualCount 实际完成数
     * @param planCount   单日目标数
     * @param workdays    工作天数
     * @param percent     该考核项的占比
     * @return
     */
    private BigDecimal calcKPIPercent(Integer actualCount, BigDecimal planCount, BigDecimal percent, BigDecimal workdays, boolean limit) {
        if (null == actualCount || null == planCount || null == workdays || null == percent) {
            return BigDecimal.ZERO;
        }
        // 如果 实际完成数*KPI增加系数 大于 单日目标数*工作日进行比较 就取1，否则取 ((实际完成数*KPI增加系数)/(单日目标数*工作日进行比较))*该考核项的占比
        return calcFinishPercent(actualCount, planCount, workdays, limit).multiply(percent).setScale(4, RoundingMode.HALF_UP);
    }

    /**
     * 计算面试KPI完成比例
     *
     * @param kpiPerson
     * @param isAM
     * @param workdays
     * @return
     */
    private BigDecimal calcKPIPercentForInterview(KPIPerson kpiPerson, boolean isAM, BigDecimal workdays) {
        BigDecimal percent1;
        BigDecimal percent2;
        BigDecimal percent3;
        BigDecimal percent4;
        BigDecimal percent5;
        BigDecimal percent6;
        if (isAM) {
            percent1 = calcFinishPercent(kpiPerson.getInterview1st(), KPIStandardConstants.am1stCount, workdays, false);
            percent2 = calcFinishPercent(kpiPerson.getInterview2nd(), KPIStandardConstants.am2ndCount, workdays, false);
            percent3 = calcFinishPercent(kpiPerson.getInterview3rd(), KPIStandardConstants.am3rdCount, workdays, false);
            percent4 = calcFinishPercent(kpiPerson.getInterview4th(), KPIStandardConstants.am4thCount, workdays, false);
            percent5 = calcFinishPercent(kpiPerson.getInterview5th(), KPIStandardConstants.am5thCount, workdays, false);
            percent6 = calcFinishPercent(kpiPerson.getInterviewFinal(), KPIStandardConstants.am6thCount, workdays, false);
            // 把6轮面试完成率加在一起
            BigDecimal sum = percent1.add(percent2).add(percent3).add(percent4).add(percent5).add(percent6);
            return sum.compareTo(BigDecimal.ONE) > 0 ? BigDecimal.ONE.multiply(KPIStandardConstants.amInterviewPercent) : sum.multiply(KPIStandardConstants.amInterviewPercent);
        } else {
            percent1 = calcFinishPercent(kpiPerson.getInterview1st(), KPIStandardConstants.re1stCount, workdays, false);
            percent2 = calcFinishPercent(kpiPerson.getInterview2nd(), KPIStandardConstants.re2ndCount, workdays, false);
            percent3 = calcFinishPercent(kpiPerson.getInterview3rd(), KPIStandardConstants.re3rdCount, workdays, false);
            percent4 = calcFinishPercent(kpiPerson.getInterview4th(), KPIStandardConstants.re4thCount, workdays, false);
            percent5 = calcFinishPercent(kpiPerson.getInterview5th(), KPIStandardConstants.re5thCount, workdays, false);
            percent6 = calcFinishPercent(kpiPerson.getInterviewFinal(), KPIStandardConstants.re6thCount, workdays, false);
            // 把6轮面试完成率加在一起
            BigDecimal sum = percent1.add(percent2).add(percent3).add(percent4).add(percent5).add(percent6);
            return sum.compareTo(BigDecimal.ONE) > 0 ? BigDecimal.ONE.multiply(KPIStandardConstants.reInterviewPercent) : sum.multiply(KPIStandardConstants.reInterviewPercent);
        }
    }

    /**
     * 计算完成比例
     *
     * @param actualCount 实际完成数量
     * @param planCount   计划完成数量
     * @param workdays    工作天数
     * @param limit       是否限制完成比例
     * @return
     */
    private BigDecimal calcFinishPercent(Integer actualCount, BigDecimal planCount, BigDecimal workdays, boolean limit) {
        if (null == actualCount || null == planCount || null == workdays) {
            return BigDecimal.ZERO;
        }
        if (limit) {
            // 需要限制完成比例
            // 实际完成数*KPI增加系数 与 计划完成数*工作天数 进行比较
            if (new BigDecimal(actualCount).multiply(KPIStandardConstants.plusRate).compareTo(planCount.multiply(workdays)) > 0) {
                // 如果实际完成数*KPI增加系数 大于 计划完成数*工作天数 就返回1
                return BigDecimal.ONE;
            } else {
                // 如果实际完成数*KPI增加系数 小于 计划完成数*工作天数 就返回实际完成数*KPI增加系数 除 计划完成数*工作天数
                return new BigDecimal(actualCount).multiply(KPIStandardConstants.plusRate).divide(planCount.multiply(workdays), 4, RoundingMode.HALF_UP);
            }
        } else {
            // 不需要限制完成比例
            // 实际完成数*KPI增加系数 除以 计划完成数量*工作天数
            return new BigDecimal(actualCount).multiply(KPIStandardConstants.plusRate).divide(planCount.multiply(workdays), 4, RoundingMode.HALF_UP);
        }
    }

    /**
     * 下载KPI Excel文件
     *
     * @param response
     */
    public void downloadKPI(List<String> dates, String scope, UserVO userVO, HttpServletResponse response, boolean kpiOnlyShowCheck) {
        // 获取业务数据
        LocalDate start = LocalDate.parse(dates.get(0).substring(0, 10));
        LocalDate end = LocalDate.parse(dates.get(1).substring(0, 10));
        List<KPIPerson> kpiList = calcKPI(start, end, scope, userVO, kpiOnlyShowCheck);
        // 封装返回response
        EasyExcelUtil.downloadExcel(response, "kpi", null, kpiList, KPIPerson.class);
    }

    /**
     * 通过开始时间、结束时间、录入人 查找评论
     *
     * @param beginDate
     * @param endDate
     * @param userName
     * @return
     */
    public List<CommentVO> findCommentsByTimeAndUsername(String beginDate, String endDate, String userName) {
        // 拿到前台传入的日期要进行+1操作。因为前端给的日期是差1天。
        LocalDate start = LocalDate.parse(beginDate.substring(0, 10));
        LocalDate end = LocalDate.parse(endDate.substring(0, 10));
        LocalDateTime startDT = LocalDateTime.of(start.getYear(), start.getMonthValue(), start.getDayOfMonth(), 0, 0, 0);
        LocalDateTime endDT = LocalDateTime.of(end.getYear(), end.getMonthValue(), end.getDayOfMonth(), 23, 59, 59);
        List<Comment> commentList = commentRepository.findByInputTimeBetweenAndUsername(startDT, endDT, userName);
        Map<Integer, Candidate> candidateMap = new HashMap<>();
        List<CommentVO> commentVOList = new ArrayList<>();
        for (Comment comment : commentList) {
            CommentVO vo = new CommentVO();
            BeanUtils.copyProperties(comment, vo);
            if (!candidateMap.containsKey(comment.getCandidateId())) {
                Optional<Candidate> candidateOptional = candidateRepository.findById(comment.getCandidateId());
                candidateOptional.ifPresent(x -> candidateMap.put(x.getId(), x));
            }
            if (candidateMap.containsKey(vo.getCandidateId())) {
                vo.setCandidateName(candidateMap.get(vo.getCandidateId()).getChineseName());
            }
            commentVOList.add(vo);
        }
        commentVOList.sort(new Comparator<CommentVO>() {
            @Override
            public int compare(CommentVO o1, CommentVO o2) {
                int compare = phaseConvertToInt(o1.getPhase()).compareTo(phaseConvertToInt(o2.getPhase()));
                if (0 == compare) {
                    return o1.getInputTime().compareTo(o2.getInputTime());
                } else {
                    return 0 - compare;
                }
            }
        });
        commentVOList.sort(Comparator.comparing(CommentVO::getInputTime));
        commentVOList.sort(Comparator.comparing(CommentVO::getCandidateId));
        return commentVOList;
    }

    private Integer phaseConvertToInt(String phase) {
        int result = 0;
        switch (phase) {
            case "TI":
                result = 1;
                break;
            case "VI":
                result = 2;
                break;
            case "IOI":
                result = 3;
                break;
            case "CVO":
                result = 4;
                break;
            case "1st Interview":
                result = 5;
                break;
            case "Offer Signed":
                result = 6;
                break;
            case "On Board":
                result = 7;
                break;
        }
        return result;
    }

    /**
     * 通过评论查询候选人 查询分页
     *
     * @param search 搜索关键字
     * @return
     */
    public List<CandidateVO> queryCandidateByCommentLimit100(String search) {
        List<Comment> commentList = commentRepository.findByContentLikeOrderByIdDesc(search);
        return commentList.stream()
                .map(a -> a.getCandidateId()).distinct().sorted(new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return 0 - o1.compareTo(o2);
                    }
                }).limit(100)
                .map(x -> candidateRepository.findById(x))
                .map(y -> TransferUtil.transferTo(CommonUtils.calcAge(y.get()), CandidateVO.class)).collect(Collectors.toList());
    }

    /**
     * 查询interviewPlan数据
     *
     * @param range
     * @param userVO
     * @return
     */
    public List<InterviewPlanVO> queryInterviewPlan(String range, UserVO userVO) {
        List<InterviewPlanVO> list = new ArrayList<>();
        // 组装数据
        List<String> phaseList = Arrays.asList("1st Interview", "2nd Interview", "3rd Interview", "4th Interview", "5th Interview", "6th Interview", "Final Interview");
        LocalDate ld = LocalDate.now();
        LocalDateTime ldt = LocalDateTime.of(ld.getYear(), ld.getMonthValue(), ld.getDayOfMonth(), 0, 0, 0);
        List<String> usernameList = userService.findByScope(range, userVO).stream().map(u -> u.getUsername()).collect(Collectors.toList());
        // 查询阶段为面试安排且时间大于等于今天的记录
        List<Comment> commentList = commentRepository.findByUsernameInAndPhaseInAndInterviewTimeGreaterThanEqual(usernameList, phaseList, ldt);
        // 所有记录按时间排序
        commentList.sort(Comparator.comparing(Comment::getInterviewTime));
        // 整理数据
        commentList.forEach(c -> {
            InterviewPlanVO vo = new InterviewPlanVO();
            vo.setId(c.getId());
            vo.setClientId(c.getClientId());
            vo.setClientName(c.getClientName());
            vo.setCaseId(c.getCaseId());
            vo.setCaseTitle(c.getCaseTitle());
            vo.setCandidateId(c.getCandidateId());
            Optional<Candidate> candidateOptional = candidateRepository.findById(c.getCandidateId());
            if (candidateOptional.isPresent()) {
                vo.setCandidateName(candidateOptional.get().getChineseName());
            }
            vo.setPhase(c.getPhase().split(" ")[0] + (Optional.ofNullable(c.getIsFinal()).orElse(false) ? " (F)" : ""));
            vo.setUsername(c.getUsername());
            vo.setInterviewTime(analysisInterviewDate(c.getInterviewTime()));
            vo.setContent(c.getContent());
            Optional<ClientCase> caseOptional = caseRepository.findById(c.getCaseId());
            if (caseOptional.isPresent()) {
                vo.setCw(caseOptional.get().getCwUserName());
            }
            vo.setDistanceDays(calcDistanceDays(c.getInterviewTime()));
            // 通过公司、职位、候选人、第几轮、时间 来去重。
            List<InterviewPlanVO> subList = list.stream().filter(x -> x.getClientId().equals(vo.getClientId()) && x.getCaseId().equals(vo.getCaseId()) && x.getCandidateId().equals(vo.getCandidateId()) && x.getPhase().equals(vo.getPhase()) && x.getInterviewTime().equals(vo.getInterviewTime())).collect(Collectors.toList());
            if (subList.size() == 0) {
                list.add(vo);
            } else {
                InterviewPlanVO interviewPlanVO = subList.get(0);
                interviewPlanVO.setUsername(interviewPlanVO.getUsername() + "&" + vo.getUsername());
            }
        });
        return list;
    }

    /**
     * 分析面试时间
     *
     * @param ldt
     * @return
     */
    private String analysisInterviewDate(LocalDateTime ldt) {
        if (null == ldt) {
            return "";
        } else {
            return String.format("%s月%s日(%s) %02d:%02d", ldt.getMonthValue(), ldt.getDayOfMonth(), transformDayOfWeek(ldt.getDayOfWeek().getValue()), ldt.getHour(), ldt.getMinute());
        }
    }

    /**
     * 转换日期显示
     *
     * @param i
     * @return
     */
    private String transformDayOfWeek(int i) {
        switch (i) {
            case 1:
                return "周一";
            case 2:
                return "周二";
            case 3:
                return "周三";
            case 4:
                return "周四";
            case 5:
                return "周五";
            case 6:
                return "周六";
            case 7:
                return "周日";
        }
        return "";
    }

    /**
     * 计算还有几天面试
     *
     * @param interviewDay
     * @return
     */
    private int calcDistanceDays(LocalDateTime interviewDay) {
        if (null == interviewDay) {
            return 0;
        } else {
            return (int) Duration.between(LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth(), 0, 0, 0), interviewDay).toDays();
        }
    }

    /**
     * 面试安排分页查询
     *
     * @param search
     * @param currentPage
     * @param pageSize
     * @return
     */
    public Page<InterviewVO> queryInterviewPage(QueryInterviewSearchRequest search, Integer currentPage, Integer pageSize) {
        Pageable pageable = new PageRequest(currentPage - 1, pageSize, Sort.Direction.DESC, "id");
        Specification<Comment> specification = new Specification<Comment>() {
            @Override
            public Predicate toPredicate(Root<Comment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (null != search) {
                    if (Strings.isNotBlank(search.getClientName())) {
                        if (search.getClientName().equals("理想-集团")) {
                            list.add(criteriaBuilder.like(root.get("clientName"), "%理想%"));
                        } else if (search.getClientName().equals("一汽-集团")) {
                            list.add(criteriaBuilder.like(root.get("clientName"), "%一汽%"));
                        } else if (search.getClientName().equals("宝马-集团")) {
                            list.add(criteriaBuilder.or(
                                    criteriaBuilder.like(root.get("clientName"), "%宝马%")
                                    , criteriaBuilder.like(root.get("clientName"), "%领悦%")));
                        } else if (search.getClientName().equals("沃尔沃-集团")) {
                            list.add(criteriaBuilder.or(
                                    criteriaBuilder.like(root.get("clientName"), "%沃尔沃%")
                                    , criteriaBuilder.equal(root.get("clientName"), "亚欧汽车制造（台州）有限公司")));
                        } else {
                            list.add(criteriaBuilder.equal(root.get("clientName"), search.getClientName()));
                        }
                    }
                    if (Strings.isNotBlank(search.getTitle())) {
                        list.add(criteriaBuilder.like(root.get("caseTitle"), "%" + search.getTitle() + "%"));
                    }
                    if (Strings.isNotBlank(search.getLoginName())) {
                        list.add(criteriaBuilder.equal(root.get("username"), search.getLoginName()));
                    }
                    if (Strings.isNotBlank(search.getPhase())) {
                        // 指定某个阶段
                        list.add(criteriaBuilder.equal(root.get("phase"), search.getPhase()));
                    } else if (Optional.ofNullable(search).map(s -> s.getAllInterview()).orElse(true)) {
                        // 未指定某个阶段，并且勾选了全部面试
                        list.add(criteriaBuilder.isNotNull(root.get("interviewTime")));
                    }
                    if (null != search.getStartDate()) {
                        list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("inputTime"), LocalDateTime.of(search.getStartDate().getYear(), search.getStartDate().getMonthValue(), search.getStartDate().getDayOfMonth(), 0, 0, 0)));
                    }
                    if (null != search.getEndDate()) {
                        list.add(criteriaBuilder.lessThanOrEqualTo(root.get("inputTime"), LocalDateTime.of(search.getEndDate().getYear(), search.getEndDate().getMonthValue(), search.getEndDate().getDayOfMonth(), 0, 0, 0)));
                    }
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        };
        Page<Comment> all = commentRepository.findAll(specification, pageable);
        Page<InterviewVO> map = all.map(x -> TransferUtil.transferTo(x, InterviewVO.class));
        map.getContent().parallelStream().forEach(i -> {
            i.setInterviewTimeStr(analysisInterviewDate(i.getInterviewTime()));
            if (null != i.getCandidateId()) {
                Optional<Candidate> optional = candidateRepository.findById(i.getCandidateId());
                if (optional.isPresent()) {
                    i.setCandidateName(optional.get().getChineseName());
                }
            }
            // 处理面试阶段
            i.setPhase(i.getPhase().split(" ")[0]);
            // 处理是否是Final
            if (Optional.ofNullable(i.getIsFinal()).orElse(false)) {
                i.setPhase(i.getPhase() + " (F)");
            }
        });
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()),
                all.getTotalElements());
        return map;
    }
}
