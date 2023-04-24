package com.hello.background.service;

import com.hello.background.constant.HolidayApproveStatusEnum;
import com.hello.background.constant.HolidayConstants;
import com.hello.background.domain.Holiday;
import com.hello.background.repository.HolidayRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

/**
 * 公共服务
 *
 * @author wuketao
 * @date 2023/4/24
 * @Description
 */
@Slf4j
@Transactional
@Service
public class CommonService {

    @Autowired
    private HolidayRepository holidayRepository;

    /**
     * 计算开始日期到结束日期扣除假期和请假后有多少个工作日。
     *
     * @return
     */
    public BigDecimal calcWorkdaysBetween(LocalDate start, LocalDate end, String userName) {
        BigDecimal workdays = BigDecimal.ZERO;
        // 查询请假情况
        List<Holiday> leaveList = holidayRepository.findAllByHolidayDateBetweenAndUserNameAndApproveStatus(Jsr310Converters.LocalDateToDateConverter.INSTANCE.convert(start), Jsr310Converters.LocalDateToDateConverter.INSTANCE.convert(end), userName, HolidayApproveStatusEnum.APPROVED);
        while (true) {
            // 计算当天的简单表示方法
            String todayStr = String.format("%d月%d日", start.getMonthValue(), start.getDayOfMonth());
            // 首先判断是否是周末
            if (start.getDayOfWeek() == DayOfWeek.SATURDAY || start.getDayOfWeek() == DayOfWeek.SUNDAY) {
                //  如果当天是周末，判断是否是调休的工作日
                if (HolidayConstants.workday2023.contains(todayStr)) {
                    // 是周末的调休日，需要计算到工作天数中
                    workdays = workdays.add(leaveTime(start, leaveList));
                }
            } else {
                // 非周末，判断是否是公休
                if (!HolidayConstants.holiday2023.contains(todayStr)) {
                    // 不是公休，需要计算到工作天数中
                    // 判断是否有请假
                    workdays = workdays.add(leaveTime(start, leaveList));
                }
            }
            // 计算日期向后+1，如果超过结束日期就终止；
            start = start.plusDays(1);
            if (start.compareTo(end) > 0) {
                break;
            }
        }
        return workdays;
    }

    /**
     * 计算是否有请假
     *
     * @param date
     * @param leaveList
     * @return
     */
    public BigDecimal leaveTime(LocalDate date, List<Holiday> leaveList) {
        // 判断是否有请假
        for (Holiday holiday : leaveList) {
            if (null != holiday.getHolidayDate() && null != holiday.getHolidayLong()) {
                LocalDate localDate = Jsr310Converters.DateToLocalDateConverter.INSTANCE.convert(holiday.getHolidayDate());
                if (localDate.equals(date)) {
                    // 有请假，返回请假剩余工作时间
                    return BigDecimal.ONE.subtract(holiday.getHolidayLong());
                }
            }
        }
        // 没有请假，直接返回1；
        return BigDecimal.ONE;
    }
}
