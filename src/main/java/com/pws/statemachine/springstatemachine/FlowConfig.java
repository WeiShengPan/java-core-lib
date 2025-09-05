package com.pws.statemachine.springstatemachine;

import lombok.Data;

import java.util.List;

@Data
public class FlowConfig {
    private String flowId;
    private String initial;
    private List<String> endStates;
    private List<StateConfig> states;
    private List<EventConfig> events;
    private List<TransitionConfig> transitions;

    // 可根据需要添加 parallelRegions、version、description 等字段
    @Data
    public static class StateConfig {
        private String id;
        private String name;
        private Integer order;
        private String entryAction, exitAction;
    }

    @Data
    public static class EventConfig {
        private String id;
        private String name;
    }

    @Data
    public static class TransitionConfig {
        private String from;
        private String to;
        private String event;
        private String guard;   // Bean 名称或 SpEL（以 "spel:" 开头）
        private String action;  // Bean 名称
    }
}
