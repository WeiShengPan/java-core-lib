package com.pws.statemachine.springstatemachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class SiteStatusServiceImpl {
    @Autowired
    private FlowStateMachineFactory factory;
    @Autowired
    StateMachineService<String,String> smService;
    @Autowired
    private StateMachineRuntimePersister<String, String, String> persister;

    public String process(long orderId, String flowId, String event, Map<String, String> payload) throws Exception {
        StateMachine<String, String> sm = factory.buildMachine(flowId);


        sm.start();

//        Mono<Void> start = sm.startReactively();
//        start.subscribe()


        sm.getExtendedState().getVariables().putAll(payload);


        boolean ok = sm.sendEvent(event);
        String now = sm.getState().getId();
        sm.stop();


        sm.sendEvent(Mono.empty()).subscribe();



//        // prepare event and extended variables
//        Message<String> msg = MessageBuilder.withPayload(payload.get("event").toString())
//                .setHeader("orderId", orderId).build();
//        payload.forEach((k,v) -> sm.getExtendedState().getVariables().put(k, v));
//
//        return sm.startReactively()
//                .thenMany(sm.sendEvent(Mono.just(msg)))
//                .then(Mono.just(sm.getState().getId()))
//                .delayUntil(s -> sm.stopReactively())
//                .map(state -> FlowResult.of(orderId, flowId, msg.getPayload(), state)).toString();





        return String.format("order=%d, flow=%s, event=%s -> ok=%s,nextState=%s",
                orderId, flowId, event, ok, now);
    }

//    @Autowired
//    private StateMachine<String,String> stateMachine;

//    public Mono<String> sendReactive(String event) {
//        return stateMachine
//                .<StateMachineReactiveLifecycle>getExtendedState()
//                .getVariables()
//                // start reactively, send event and then stop
//                .then(StateMachineReactiveLifecycle.startReactively(stateMachine))
//                .then(stateMachine.sendEvent(Mono.just(MessageBuilder.withPayload(event).build())))
//                .flatMap(result -> {
//                    // result is StateMachineEventResult
//                    return Mono.just(stateMachine.getState().getId());
//                })
//                .delayUntil(s -> StateMachineReactiveLifecycle.stopReactively(stateMachine));
//    }
//
//    public Mono<String> processEventReactive(String event) {
//        return StateMachineReactiveLifecycle.startReactively(stateMachine)
//                .then(stateMachine.sendEvent(Mono.just(MessageBuilder.withPayload(event).build())))
//                .flatMap(r -> Mono.just(stateMachine.getState().getId()))
//                .delayUntil(s -> StateMachineReactiveLifecycle.stopReactively(stateMachine));
//    }

//    public FlowResult sendEvent( String id,
//                                String flowId,
//                                 Map<String,Object> payload) throws Exception {
//        StateMachine<String,String> sm = factory.buildMachine(flowId);
//
//        // restore 来自持久存储的状态
//        smService.acquireStateMachine(id); // restore
//        sm.start();  // 调用 start()
//        sm.getExtendedState().getVariables().putAll(payload);
//        boolean accepted = sm.sendEvent(payload.get("event").toString());
//        smService.releaseStateMachine(id); // persist
//        sm.stop();
//
//        return FlowResult.of(id, flowId, payload.get("event").toString(), sm.getState().getId());
//    }

}
