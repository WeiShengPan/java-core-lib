package com.pws.spring.controller;

import com.pws.spring.factorybean.FactoryBeanTestService;
import com.pws.spring.getbean.PersonApplicationContextAware;
import com.pws.spring.getbean.PersonApplicationListener;
import com.pws.spring.getbean.PersonBeanFactoryAware;
import com.pws.spring.scope.ScopeTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wilson.pan
 * @date 2021/2/26
 */
@Slf4j
@RestController
@RequestMapping("/main/")
public class MainController {

    @Autowired
    private FactoryBeanTestService factoryBeanTestService;

    @Autowired
    private ScopeTestService scopeTestService;

    @RequestMapping(value = "test-factory-bean", method = RequestMethod.GET)
    public void testFactoryBean() {
        factoryBeanTestService.sayHello();
    }

    @RequestMapping(value = "test-get-bean", method = RequestMethod.GET)
    public void testGetBean() {
        PersonApplicationContextAware.sayHello();
        PersonApplicationListener.sayHello();
        PersonBeanFactoryAware.sayHello();
    }

    @RequestMapping(value = "test-custom-scope", method = RequestMethod.GET)
    public void testCustomScope() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            executorService.submit(() -> {
                scopeTestService.sayHello();
                scopeTestService.sayHello();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.info(e.getMessage(), e);
                }
            });
        }
    }
}
