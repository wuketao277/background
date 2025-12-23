package com.hello.background.service;

import com.hello.background.domain.PRCComment;
import com.hello.background.repository.PRCCommentRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.PRCCommentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuketao
 * @date 2025/12/23
 * @Description
 */
@Slf4j
@Transactional
@Service
public class PRCCommentService {

    @Autowired
    private PRCCommentRepository prcCommentRepository;

    /**
     * 通过id删除
     *
     * @param id
     */
    public void deleteById(Integer id) {
        prcCommentRepository.deleteById(id);
    }

    /**
     * 保存评论信息
     *
     * @param vo
     * @return
     */
    public PRCCommentVO save(PRCCommentVO vo) {
        PRCComment prcComment = TransferUtil.transferTo(vo, PRCComment.class);
        PRCComment resultComment = prcCommentRepository.save(prcComment);
        return TransferUtil.transferTo(resultComment, PRCCommentVO.class);
    }

    /**
     * 通过PRCID查询所有评论
     *
     * @param prcId
     * @return
     */
    public List<PRCCommentVO> findAllByPRCIdOrderByDesc(Integer prcId) {
        List<PRCComment> allByPRCId = prcCommentRepository.findAllByPrcIdOrderByIdDesc(prcId);
        List<PRCCommentVO> collect = allByPRCId.stream().map(comment -> TransferUtil.transferTo(comment, PRCCommentVO.class)).collect(Collectors.toList());
        return collect;
    }
}
