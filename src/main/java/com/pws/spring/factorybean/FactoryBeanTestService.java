package com.pws.spring.factorybean;

import com.pws.spring.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Service;

/**
 * @author wilson.pan
 * @date 2021/2/26
 */
@Slf4j
@Service
public class FactoryBeanTestService implements BeanFactoryAware {

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public void sayHello() {
        // 获取的是 PersonFactoryBean 中 getObject 方法返回的值
        Person person = (Person) beanFactory.getBean("personFactoryBean");
        log.info(person.toString());

        Object object1 = beanFactory.getBean(Person.class);
        log.info(object1.toString());

        // 获取的是 PersonFactoryBean 对象
        PersonFactoryBean personFactoryBean = (PersonFactoryBean) beanFactory.getBean("&personFactoryBean");
        log.info(personFactoryBean.toString());

        Object object2 = beanFactory.getBean(PersonFactoryBean.class);
        log.info(object2.toString());
    }
}
