package com.hello.background.service;

import com.hello.background.domain.Resume;
import com.hello.background.repository.ResumeRepository;
import com.hello.background.vo.ResumeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuketao
 * @date 2019/12/14
 * @Description
 */
@Transactional
@Slf4j
@Service
public class ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    /**
     * 保存候选人
     *
     * @param vo
     * @return
     */
    public void save(ResumeVO vo) {
        // 通过候选人id，删除旧的简历信息
        resumeRepository.deleteByCandidateId(vo.getCandidateId());
        // 将简历内容拆分成多部分进行保存。
        List<Resume> resumeList = transferToResumeList(vo);
        resumeList.stream().forEach(x -> resumeRepository.save(x));
    }

    /**
     * 转换为数据库对象列表
     *
     * @param vo
     * @return
     */
    public List<Resume> transferToResumeList(ResumeVO vo) {
        List<Resume> resumeList = new ArrayList<>();
        // 防止程序异常，最多拆分成50个小段
        int index = 50;
        // 剩余的简历内容
        String oldContent = vo.getContent();
        while (!StringUtils.isEmpty(oldContent) && index > 0) {
            index--;
            String partContent = "";
            if (oldContent.length() <= 2000) {
                partContent = oldContent;
                oldContent = "";
            } else {
                String temp = oldContent.substring(0, 2000);
                int blankIndex = temp.lastIndexOf(" ");
                int juhaoIndex = temp.lastIndexOf("。");
                int douhaoIndex = temp.lastIndexOf("，");
                int splitIndex = blankIndex >= juhaoIndex ? blankIndex >= douhaoIndex ? blankIndex : douhaoIndex :
                        juhaoIndex >= douhaoIndex ? juhaoIndex : douhaoIndex;
                partContent = oldContent.substring(0, splitIndex);
                oldContent = oldContent.substring(splitIndex);
            }
            Resume resume = new Resume();
            resume.setCandidateId(vo.getCandidateId());
            resume.setContent(partContent);
            resumeList.add(resume);
        }
        return resumeList;
    }


    /**
     * 通过id
     *
     * @param id
     */
    public void deleteById(Integer id) {
        resumeRepository.deleteById(id);
    }

    /**
     * 通过候选人id获取简历信息
     *
     * @param candidateId
     * @return
     */
    public String findResumeByCandidateId(String candidateId) {
        List<Resume> resumeList = resumeRepository.findByCandidateIdOrderById(Integer.valueOf(candidateId));
        StringBuilder sb = new StringBuilder();
        resumeList.forEach(x -> sb.append(x.getContent()));
        return sb.toString();
    }

    /**
     * 通过candidateId查找
     *
     * @param candidateId
     * @return
     */
    public List<Resume> findByCandidateIdOrderById(String candidateId) {
        return resumeRepository.findByCandidateIdOrderById(Integer.valueOf(candidateId));
    }

    /**
     * 简历内容模糊匹配
     *
     * @param content
     * @return
     */
    public List<Resume> findByContentLikeOrderByCandidateIdAscIdAsc(String content) {
        return resumeRepository.findByContentLikeOrderByCandidateIdAscIdAsc(content);
    }
}
