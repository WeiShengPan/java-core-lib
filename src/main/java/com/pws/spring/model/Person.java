package com.pws.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author wilson.pan
 * @date 2021/2/26
 */
@Data
@ToString
@AllArgsConstructor
public class Person {

    private String firstName;

    private String lastName;
}
