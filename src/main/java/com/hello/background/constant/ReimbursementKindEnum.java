package com.hello.background.constant;

/**
 * 报销项目枚举
 *
 * @author wuketao
 * @date 2022/10/11
 * @Description
 */
public enum ReimbursementKindEnum {
    InternalAirTicket("国内机票"),
    InternalTrainTicket("国内高铁/火车"),
    TaxiSubway("出租车/地铁/其他市内交通"),
    TravelHotel("差旅住宿费"),
    TravelMeal("差旅餐饭"),
    Communication("通讯费");

    private String name;

    ReimbursementKindEnum(String _name) {
        this.name = _name;
    }
}
