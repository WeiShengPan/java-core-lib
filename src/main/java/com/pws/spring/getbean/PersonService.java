package com.pws.spring.getbean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wilson.pan
 * @date 2021/2/26
 */
@Slf4j
@Service
public class PersonService {

    public void sayHello(String msg) {
        log.info("PersonService say hello: {}", msg);
    }

}
