package com.hello.background.service;

import com.hello.background.common.CommonUtils;
import com.hello.background.domain.Candidate;
import com.hello.background.domain.ClientCase;
import com.hello.background.domain.Comment;
import com.hello.background.repository.CandidateRepository;
import com.hello.background.repository.CaseRepository;
import com.hello.background.repository.CommentRepository;
import com.hello.background.utils.EasyExcelUtil;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
     * 计算KPI
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return
     */
    public List<KPIPerson> calcKPI(LocalDate start, LocalDate end, String scope, UserVO userVO) {
        // 获取计算时间段
        LocalDateTime startDT = LocalDateTime.of(start.getYear(), start.getMonthValue(), start.getDayOfMonth(), 0, 0, 0);
        LocalDateTime endDT = LocalDateTime.of(end.getYear(), end.getMonthValue(), end.getDayOfMonth(), 23, 59, 59);
        // 通过Scope获取用户集合
        List<UserVO> userVOList = userService.findByScope(scope, userVO);
        // 获取该时间段内指定用户所有评论
        List<Comment> commentList = commentRepository.findByInputTimeBetweenAndUsernameIn(startDT, endDT, userVOList.stream().map(u -> u.getUsername()).collect(Collectors.toList()));
        List<KPIPerson> kpiPersonList = new ArrayList<>();
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
                case "Offer Signed":
                    kpiPerson.setOfferSigned(kpiPerson.getOfferSigned() + 1);
                    break;
                case "On Board":
                    kpiPerson.setOnBoard(kpiPerson.getOnBoard() + 1);
                    break;
            }
        }
        kpiPersonList.sort(Comparator.comparing(KPIPerson::getUserId));
        return kpiPersonList;
    }

    /**
     * 下载KPI Excel文件
     *
     * @param response
     */
    public void downloadKPI(List<String> dates, String scope, UserVO userVO, HttpServletResponse response) {
        // 获取业务数据
        LocalDate start = LocalDate.parse(dates.get(0).substring(0, 10));
        LocalDate end = LocalDate.parse(dates.get(1).substring(0, 10));
        List<KPIPerson> kpiList = calcKPI(start, end, scope, userVO);
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
        List<String> phaseList = Arrays.asList("1st Interview", "2nd Interview", "3rd Interview", "4th Interview", "Final Interview");
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
            vo.setPhase(c.getPhase().split(" ")[0]);
            vo.setUsername(c.getUsername());
            vo.setInterviewTime(analysisInterviewDate(c.getInterviewTime()));
            vo.setContent(c.getContent());
            Optional<ClientCase> caseOptional = caseRepository.findById(c.getCaseId());
            if (caseOptional.isPresent()) {
                vo.setCw(caseOptional.get().getCwUserName());
            }
            vo.setDistanceDays(calcDistanceDays(c.getInterviewTime()));
            list.add(vo);
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
}
