package com.pws.spring.scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

/**
 * 自定义 Scope，本例实现同一个线程中获取的 Spring bean 都是同一个对象，也就是 TreadLocalScope
 * 定义完需要注入到Spring容器中：BeanFactoryPostProcessor
 *
 * @author wilson.pan
 * @date 2021/2/26
 */
public class CustomizeScope implements Scope {

    public static final String THREAD_LOCAL_SCOPE = "threadLocalScope";

    private static final ThreadLocal<Object> TLS = new ThreadLocal<>();

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        Object val = TLS.get();

        if (val == null) {
            TLS.set(objectFactory.getObject());
            val = TLS.get();
        }

        return val;
    }

    @Override
    public Object remove(String name) {
        TLS.remove();
        return null;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

    @Override
    public String getConversationId() {
        return null;
    }
}
