package com.hello.background.service;

import com.hello.background.domain.SuccessfulPerm;
import com.hello.background.repository.SuccessfulPermRepository;
import com.hello.background.vo.QueryGeneralReportRequest;
import com.hello.background.vo.QueryGeneralReportResponse;
import com.hello.background.vo.QueryGeneralReportResponseKeyValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 * 报告服务
 *
 * @author wuketao
 * @date 2019/12/14
 * @Description
 */
@Transactional
@Slf4j
@Service
public class ReportService {
    @Autowired
    private SuccessfulPermRepository successfulPermRepository;

    public QueryGeneralReportResponse queryGeneral(QueryGeneralReportRequest request) {
        QueryGeneralReportResponse response = new QueryGeneralReportResponse();
        try {
            Iterable<SuccessfulPerm> iterable = successfulPermRepository.findAll();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startDate = sdf.parse(String.format("%s-%s-%s %s:%s:%s", request.getStartDate().getYear(), request.getStartDate().getMonthValue(), request.getStartDate().getDayOfMonth(), 0, 0, 0));
            Date endDate = sdf.parse(String.format("%s-%s-%s %s:%s:%s", request.getEndDate().getYear(), request.getEndDate().getMonthValue(), request.getEndDate().getDayOfMonth(), 23, 59, 59));
            // offer signed 数据
            Iterator<SuccessfulPerm> iterator = iterable.iterator();
            while (iterator.hasNext()) {
                SuccessfulPerm s = iterator.next();
                if ("approved".equals(s.getApproveStatus()) && s.getOfferDate() != null
                        && s.getOfferDate().compareTo(startDate) >= 0
                        && endDate.compareTo(s.getOfferDate()) >= 0) {
                    response.getOfferDateData().add(new QueryGeneralReportResponseKeyValue(s.getCandidateChineseName(), s.getBilling()));
                    response.setOfferDateBilling(response.getOfferDateBilling() + s.getBilling());
                }
            }

        } catch (Exception ex) {
        }
        return response;
    }
}
