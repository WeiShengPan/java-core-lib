package com.pws.spring.aspect;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wilson.pan
 * @date 2021/3/1
 */
@Slf4j
@Service
public class AspectTestService {

    @Auth(type = Auth.AuthType.READ)
    public void sayHello() {
        log.info("AspectTestService say hello: {}", this.toString());
    }
}
