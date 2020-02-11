package org.easyspring.test.entity;

import org.easyspring.beans.factory.annotation.Autowired;

public class Teacher {
    @Autowired
    protected Student student;

    /*
    循环依赖，会造成死循环，堆内存溢出
    @Autowired
    protected School school;
    */
}
