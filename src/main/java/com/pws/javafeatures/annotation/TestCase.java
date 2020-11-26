package com.pws.javafeatures.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author panws
 * @since 2017-08-18
 */
@Target(ElementType.METHOD) @Retention(RetentionPolicy.RUNTIME) public @interface TestCase {

	int id() default 0;

	String desc();

}
