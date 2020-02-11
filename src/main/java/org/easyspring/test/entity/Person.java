package org.easyspring.test.entity;

import org.easyspring.beans.factory.annotation.Autowired;
import org.easyspring.beans.factory.annotation.Value;
import org.easyspring.stereotype.Component;

@Component(value = "user")
public class Person {
    @Value("tancunshi")
    public String name;
    @Value("20")
    public int age;
    @Value("true")
    public boolean male;
    @Autowired
    private School school;

    public School getSchool() {
        return school;
    }
}
