package com.hello.background.domainservice;

import com.hello.background.domain.MyNews;
import com.hello.background.repository.MyNewsRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.MyNewsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 新闻领域服务类
 *
 * @author wuketao
 * @date 2019/11/24
 * @Description
 */
@Service
public class MyNewsDomainService {

    @Autowired
    private MyNewsRepository myNewsRepository;

    /**
     * 从DO对象创建VO对象
     *
     * @param myNews DO对象
     * @return VO对象
     */
    public MyNewsVO fromDOToVO(MyNews myNews) {
        MyNewsVO vo = new MyNewsVO();
        TransferUtil.transfer(myNews, vo);
        vo.setCreateUserName("Ramona");
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

    public boolean create(MyNewsVO vo) {
        MyNews myNews = myNewsRepository.save(fromVOToDO(vo));
        if (null != myNews && null != myNews.getId()) {
            return true;
        } else {
            return false;
        }
    }
}
