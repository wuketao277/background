package com.hello.background.service;

import com.hello.background.domain.Comment;
import com.hello.background.repository.CommentRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.CalcKPIRequest;
import com.hello.background.vo.CommentVO;
import com.hello.background.vo.KPIPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
            Optional<KPIPerson> first = kpiPersonList.stream().filter(kpi -> kpi.getName().equals(cnt.getRealname())).findFirst();
            if (first.isPresent()) {
                kpiPerson = first.get();
            } else {
                kpiPerson.setName(cnt.getRealname());
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
                case "Ist Interview":
                    kpiPerson.setInterview1st(kpiPerson.getInterview1st() + 1);
                    break;
                case "Offer Signed":
                    kpiPerson.setOfferSigned(kpiPerson.getOfferSigned());
                    break;
                case "On Board":
                    kpiPerson.setOnBoard(kpiPerson.getOnBoard() + 1);
                    break;
            }
        }
        return kpiPersonList;
    }
}
