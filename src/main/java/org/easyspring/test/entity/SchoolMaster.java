package org.easyspring.test.entity;

import org.easyspring.beans.factory.annotation.Autowired;
import org.easyspring.stereotype.Component;

@Component
public class SchoolMaster extends Teacher{
    private String name;
    private Car car;

    public String getName() {
        return name;
    }

    @Autowired
    public void setCar(Car car){
        this.car = car;
    }

    public Car getCar(){
        return this.car;
    }

    public Student getStudent(){
        return super.student;
    }
}
