package com.hello.background.constant;

/**
 * 报销项目枚举
 *
 * @author wuketao
 * @date 2022/10/11
 * @Description
 */
public enum ReimbursementKindEnum {
    Parking("停车"),
    InternalAirTicket("国内机票"),
    InternalTrainTicket("国内高铁/火车"),
    TaxiSubway("出租车/地铁/其他市内交通"),
    TravelHotel("差旅住宿费"),
    TravelMeal("差旅餐饭"),
    Communication("通讯费"),
    OfficeRent("办公室租金"),
    Training("培训费"),
    Print("打印费"),
    Tool("文具费"),
    Postage("快递费"),
    Drug("药品"),
    Candidate("候选人招待费"),
    Client("客户招待费"),
    Employee("员工内部招待费"),
    Consultant("外包员工招待费"),
    BodyCheck("体检费"),
    Recruit("招聘费"),
    Other("其他");

    private String name;

    ReimbursementKindEnum(String _name) {
        this.name = _name;
    }
}
