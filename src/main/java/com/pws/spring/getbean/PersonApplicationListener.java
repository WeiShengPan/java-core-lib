package com.pws.spring.getbean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * Get Spring bean by implement ApplicationListener
 * @author wilson.pan
 * @date 2021/2/26
 */
@Service
public class PersonApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private static ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        applicationContext = contextRefreshedEvent.getApplicationContext();
    }

    public static void sayHello() {
        PersonService personService = applicationContext.getBean(PersonService.class);
        personService.sayHello("From ApplicationListener");
    }
}
