package com.pws.spring.scope;

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
public class ScopeTestService implements BeanFactoryAware {

    private BeanFactory beanFactory;

    public void sayHello() {
        // 这里beanFactory返回的bean实例会根据不同thead返回不同实例
        log.info("{} - {}", Thread.currentThread().getName(), beanFactory.getBean(ScopedObj.class).toString());
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
