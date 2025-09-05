package com.pws.statemachine.cola;


import lombok.Getter;

@Getter
public enum SiteStatusState {

    STAY_SAMPLING("10", "待扦样"),
    STAY_QUALITY_TESTING("20", "待质检"),
    STAY_WEIGH("30", "待重车过磅"),
    STAY_TWO_WEIGH("40", "待二次过磅"),
    STAY_SETTLEMENT("50", "待结算"),
    COMPLETE("60", "已完成");

    private final String code;
    private final String name;

    SiteStatusState(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
