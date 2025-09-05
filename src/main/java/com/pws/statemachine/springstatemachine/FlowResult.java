package com.pws.statemachine.springstatemachine;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName="of")
public class FlowResult {
    private Long orderId;
    private String flowId;
    private String event;
    private String state;
}

