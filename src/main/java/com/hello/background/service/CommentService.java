package com.hello.background.service;

import com.hello.background.domain.Comment;
import com.hello.background.repository.CommentRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuketao
 * @date 2019/12/22
 * @Description
 */
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public CommentVO save(CommentVO vo) {
        Comment comment = TransferUtil.transferTo(vo, Comment.class);
        Comment resultComment = commentRepository.save(comment);
        return TransferUtil.transferTo(resultComment, CommentVO.class);
    }

    public List<CommentVO> findAllByCandidateId(Integer candidateId) {
        List<Comment> allByCandidateId = commentRepository.findAllByCandidateId(candidateId);
        return allByCandidateId.stream().map(comment -> TransferUtil.transferTo(comment, CommentVO.class)).collect(Collectors.toList());
    }
}
