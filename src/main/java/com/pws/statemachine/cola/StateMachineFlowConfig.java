package com.pws.statemachine.cola;

import lombok.Data;

import java.util.List;

@Data
public class StateMachineFlowConfig {
    private String flowId;
    private String description;
    private String version;
    private String initial;
    private List<String> endStates;
    private List<StateConfig> states;
    private List<EventConfig> events;
    private List<TransitionConfig> transitions;
    private List<StateMachineFlowConfig> parallelRegions;

    @Data
    public static class StateConfig {
        private String id;
        private String name;
        private String code;
        private Integer order;
        private String entryAction;
        private String exitAction;
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
        private String guard;
        private String action;
    }
}
