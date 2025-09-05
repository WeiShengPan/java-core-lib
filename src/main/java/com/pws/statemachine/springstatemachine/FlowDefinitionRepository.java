package com.pws.statemachine.springstatemachine;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class FlowDefinitionRepository {

    @Value("${flows.location:config/flows}")
    private String flowsDir;

    private Map<String, FlowConfig> flowConfigs = new ConcurrentHashMap<>();

    @Autowired
    private ObjectMapper mapper;

    @PostConstruct
    public void init() throws IOException {
        reloadAll();
    }

    public void reloadAll() throws IOException {
        File[] files = new File(flowsDir).listFiles(f -> f.getName().endsWith(".json"));
        Map<String, FlowConfig> newMap = new ConcurrentHashMap<>();
        if (files != null) for (File f : files) {
            FlowConfig cfg = mapper.readValue(f, FlowConfig.class);
            newMap.put(cfg.getFlowId(), cfg);
        }
        this.flowConfigs = newMap;
        log.info("Reloaded flows: " + flowConfigs.keySet());
    }

    public FlowConfig get(String flowId) {
        return flowConfigs.get(flowId);
    }
}
