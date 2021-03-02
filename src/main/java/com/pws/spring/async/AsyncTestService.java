package com.pws.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Spring build-in async features, should work with @EnableAsync and @Async
 *
 * @author wilson.pan
 * @date 2021/3/1
 */
@Slf4j
@Service
public class AsyncTestService {

    @Async
    public void sayHello() {
        Thread thread = Thread.currentThread();
        log.info("[Thread: {}_{}] AsyncTestService say hello: {}", thread.getName(), thread.getId(), this.toString());
    }
}
