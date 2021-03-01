package com.pws.spring.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author wilson.pan
 * @date 2021/2/26
 */
@Scope(CustomizeScope.THREAD_LOCAL_SCOPE)
@Component
public class ScopedObj {
}
