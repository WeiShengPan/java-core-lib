package com.pws.spring.getbean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Get Spring bean by implement ApplicationContextAware
 *
 * @author wilson.pan
 * @date 2021/2/26
 */
@Slf4j
@Service
public class PersonApplicationContextAware implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        PersonApplicationContextAware.applicationContext = applicationContext;
    }

    public static void sayHello() {
        PersonService personService = applicationContext.getBean(PersonService.class);
        personService.sayHello("From ApplicationContextAware");
    }
}
