package com.pws.spring.factorybean;

import com.pws.spring.model.Person;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * FactoryBean作为工厂来暴露对象获取，getObject()方法返回封装的bean
 *
 * @author wilson.pan
 * @date 2021/2/26
 */
@Component
public class PersonFactoryBean implements FactoryBean<Person>, InitializingBean {

    @Nullable
    private Person person;

    @Override
    public void afterPropertiesSet() {
        this.person = new Person("P", "W");
    }

    @Override
    @Nullable
    public Person getObject() throws Exception {
        return person;
    }

    @Override
    public Class<?> getObjectType() {
        return Person.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
