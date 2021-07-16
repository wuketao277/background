package com.hello.background.service;

import com.hello.background.domain.Candidate;
import com.hello.background.domain.Comment;
import com.hello.background.repository.CandidateRepository;
import com.hello.background.repository.CommentRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.CalcKPIRequest;
import com.hello.background.vo.CommentVO;
import com.hello.background.vo.KPIPerson;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wuketao
 * @date 2019/12/22
 * @Description
 */
@Transactional
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CandidateRepository candidateRepository;

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
     * @param request
     * @return
     */
    public List<KPIPerson> calcKPI(CalcKPIRequest request) {
        // 拿到前台传入的日期要进行+1操作。因为前端给的日期是差1天。
        LocalDate start = LocalDate.parse(request.getDates().get(0).substring(0, 10)).plusDays(1);
        LocalDate end = LocalDate.parse(request.getDates().get(1).substring(0, 10)).plusDays(1);
        LocalDateTime startDT = LocalDateTime.of(start.getYear(), start.getMonthValue(), start.getDayOfMonth(), 0, 0, 0);
        LocalDateTime endDT = LocalDateTime.of(end.getYear(), end.getMonthValue(), end.getDayOfMonth(), 23, 59, 59);
        List<Comment> commentList = commentRepository.findByInputTimeBetween(startDT, endDT);
        List<KPIPerson> kpiPersonList = new ArrayList<>();
        for (Comment cnt : commentList) {
            KPIPerson kpiPerson = new KPIPerson();
            Optional<KPIPerson> first = kpiPersonList.stream().filter(kpi -> kpi.getUserName().equals(cnt.getUsername())).findFirst();
            if (first.isPresent()) {
                kpiPerson = first.get();
            } else {
                kpiPerson.setRealName(cnt.getRealname());
                kpiPerson.setUserName(cnt.getUsername());
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
        return kpiPersonList;
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
        LocalDate start = LocalDate.parse(beginDate.substring(0, 10)).plusDays(1);
        LocalDate end = LocalDate.parse(endDate.substring(0, 10)).plusDays(1);
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
                vo.setChineseName(candidateMap.get(vo.getCandidateId()).getChineseName());
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
}
