package com.pws.spring.getbean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Service;

/**
 * Get Spring bean by implement BeanFactoryAware
 *
 * @author wilson.pan
 * @date 2021/2/26
 */
@Service
public class PersonBeanFactoryAware implements BeanFactoryAware {

    private static BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        PersonBeanFactoryAware.beanFactory = beanFactory;
    }

    public static void sayHello() {
        PersonService personService = beanFactory.getBean(PersonService.class);
        personService.sayHello("From BeanFactoryAware");
    }
}
