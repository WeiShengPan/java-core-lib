package com.pws.statemachine.cola;

import lombok.Getter;

@Getter
public enum SiteStatusEvent {

    DO_SAMPLE("扦样"),

    DO_QUALITY_TEST("质检"),

    DO_WEIGHT("首次过磅"),

    DO_TWO_WEIGHT("二次过磅"),

    DO_SETTLEMENT("结算");

    private final String name;

    SiteStatusEvent(String name) {
        this.name = name;
    }
}
