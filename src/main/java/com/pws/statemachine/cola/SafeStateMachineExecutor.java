package com.pws.statemachine.cola;

import com.alibaba.cola.statemachine.StateMachine;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SafeStateMachineExecutor<S, E, C> {

    private final StateMachine<S, E, C> stateMachine;

    public SafeStateMachineExecutor(StateMachine<S, E, C> stateMachine) {
        this.stateMachine = stateMachine;
    }

    /**
     * 安全执行状态转换：如果非法事件，抛出异常 + 记录日志
     */
    public S verifyAndFire(S currentState, E event, C context) {
        // 检查是否存在合法 transition
        boolean legal = stateMachine.verify(currentState, event);
        if (!legal) {
            String msg = String.format(
                    "非法事件触发！在状态 [%s] 下无法处理事件 [%s]，stateMachine=%s",
                    currentState, event, stateMachine.getMachineId()
            );
//            System.out.println(msg);
            log.error(msg);
            throw new IllegalStateException(msg);
        }

//        System.out.println("【状态流转】合法：machineId=" + stateMachine.getMachineId() + ", from=" + currentState + ", event=" + event);
        log.debug("【状态流转】合法：machineId={}, from={}, event={}", stateMachine.getMachineId(), currentState, event);

        return stateMachine.fireEvent(currentState, event, context);
    }
}
