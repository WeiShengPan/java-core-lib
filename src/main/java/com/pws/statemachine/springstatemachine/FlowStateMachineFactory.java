package com.pws.statemachine.springstatemachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class FlowStateMachineFactory {

    @Autowired
    private FlowDefinitionRepository repo;
    @Autowired
    private ApplicationContext ctx;

    public StateMachine<String, String> buildMachine(String flowId) throws Exception {
        FlowConfig cfg = repo.get(flowId);
        if (cfg == null) throw new IllegalArgumentException(flowId);

        StateMachineBuilder.Builder<String, String> builder = StateMachineBuilder.builder();

        builder.configureConfiguration()
                .withConfiguration()
                .beanFactory(ctx)
                .autoStartup(false);

        builder.configureStates()
                .withStates()
                .initial(cfg.getInitial())
                .end(Arrays.toString(cfg.getEndStates().toArray(new String[0])))
                .states(cfg.getStates().stream().map(FlowConfig.StateConfig::getId).collect(Collectors.toSet()));

        org.springframework.statemachine.config.configurers.ExternalTransitionConfigurer<String, String> t = builder.configureTransitions().withExternal();
        for (FlowConfig.TransitionConfig tc : cfg.getTransitions()) {
            t = t.source(tc.getFrom()).target(tc.getTo()).event(tc.getEvent());
            if (tc.getGuard() != null) {
                if (tc.getGuard().startsWith("spel:")) {
                    t.guardExpression(tc.getGuard().substring(5));
                } else {
                    t.guard(ctx.getBean(tc.getGuard(), Guard.class));
                }
            }
            if (tc.getAction() != null) {
                t.action(ctx.getBean(tc.getAction(), Action.class));
            }
            t.and();
        }
        return builder.build();
    }
}

