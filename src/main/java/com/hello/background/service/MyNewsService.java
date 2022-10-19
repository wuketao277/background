package com.hello.background.service;

import com.google.common.base.Strings;
import com.hello.background.domain.MyNews;
import com.hello.background.domain.User;
import com.hello.background.repository.MyNewsRepository;
import com.hello.background.repository.UserRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.MyNewsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 新闻领域服务类
 *
 * @author wuketao
 * @date 2019/11/24
 * @Description
 */
@Transactional
@Service
public class MyNewsService {

    @Autowired
    private MyNewsRepository myNewsRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * 从DO对象创建VO对象
     *
     * @param myNews DO对象
     * @return VO对象
     */
    public MyNewsVO fromDOToVO(MyNews myNews) {
        MyNewsVO vo = new MyNewsVO();
        TransferUtil.transfer(myNews, vo);
        Optional<User> userOptional = userRepository.findById(Integer.valueOf(myNews.getCreateUserId()));
        if (userOptional.isPresent()) {
            vo.setCreateUserName(userOptional.get().getUsername());
        }
        return vo;
    }

    /**
     * 从VO对象创建DO对象
     *
     * @param vo VO对象
     * @return DO对象
     */
    public MyNews fromVOToDO(MyNewsVO vo) {
        MyNews myNews = new MyNews();
        TransferUtil.transfer(vo, myNews);
        // 默认新闻创建时间为当前时间
        myNews.setCreateTime(LocalDateTime.now());
        return myNews;
    }

    /**
     * 保存新闻
     *
     * @param vo 新闻对象
     * @return 保存后的新闻对象
     */
    public MyNewsVO saveNews(MyNewsVO vo) {
        MyNews myNews = myNewsRepository.save(fromVOToDO(vo));
        if (null != myNews && null != myNews.getId()) {
            return fromDOToVO(myNews);
        } else {
            return null;
        }
    }

    /**
     * 查询新闻，分页
     *
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    public Page<MyNewsVO> queryNewsPage(String search, Integer currentPage, Integer pageSize) {
        Pageable pageable = new PageRequest(currentPage - 1, pageSize);
        Page<MyNews> myNewsPage = null;
        long total = 0;
        if (Strings.isNullOrEmpty(search)) {
            myNewsPage = myNewsRepository.findAll(pageable);
            total = myNewsRepository.count();
        } else {
            myNewsPage = myNewsRepository.findByTitleLikeOrContentLike(search, search, pageable);
            total = myNewsRepository.countByTitleLikeOrContentLike(search, search);
        }
        Page<MyNewsVO> map = myNewsPage.map(myNews -> fromDOToVO(myNews));
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()), total);
        return map;
    }

    /**
     * 获取所有新闻
     *
     * @return
     */
    public List<MyNewsVO> findAll() {
        List<MyNews> all = myNewsRepository.findAll();
        return all.stream().map(x -> TransferUtil.transferTo(x, MyNewsVO.class)).collect(Collectors.toList());
    }

    /**
     * 获取最后发布的新闻
     *
     * @return
     */
    public List<MyNewsVO> findTop10ByPublishOrderByCreateTimeDesc() {
        List<MyNews> list = myNewsRepository.findTop10ByPublishOrderByCreateTimeDesc(true);
        return list.stream().map(news -> TransferUtil.transferTo(news, MyNewsVO.class)).collect(Collectors.toList());
    }

    /**
     * 获取最后发布的新闻
     *
     * @return
     */
    public List<MyNewsVO> findTop100ByPublishOrderByCreateTimeDesc() {
        List<MyNews> list = myNewsRepository.findTop100ByPublishOrderByCreateTimeDesc(true);
        return list.stream().map(news -> TransferUtil.transferTo(news, MyNewsVO.class)).collect(Collectors.toList());
    }

    /**
     * 通过id删掉新闻
     *
     * @param id
     */
    public void deleteById(Integer id) {
        myNewsRepository.deleteById(id);
    }
}
