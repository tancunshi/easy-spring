package org.easyspring.test.entity;

import org.easyspring.beans.factory.annotation.Autowired;
import org.easyspring.stereotype.Component;

@Component(value = "user")
public class Person {
    private String name;

    @Autowired
    private School school;

    public String getName() {
        return name;
    }

    public School getSchool() {
        return school;
    }
}
