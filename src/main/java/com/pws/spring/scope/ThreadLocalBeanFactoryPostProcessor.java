package com.pws.spring.scope;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author wilson.pan
 * @date 2021/2/26
 */
@Component
public class ThreadLocalBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // Register customized scope ThreadLocalScope into Spring Container.
        beanFactory.registerScope(CustomizeScope.THREAD_LOCAL_SCOPE, new CustomizeScope());
    }
}
