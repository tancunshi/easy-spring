package org.easyspring.test.aop;

import org.easyspring.beans.factory.annotation.Autowired;
import org.easyspring.stereotype.Component;
import org.easyspring.util.MessageTracker;

@Component
public class Controller {

    @Autowired
    private Service service;

    public void sayHello(){
        MessageTracker.addTrack("hello");
        //throw new RuntimeException("测试afterThrowingAdvice");
    }
}
