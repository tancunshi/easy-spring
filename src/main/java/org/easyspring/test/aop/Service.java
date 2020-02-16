package org.easyspring.test.aop;

import org.easyspring.stereotype.Component;
import org.easyspring.util.MessageTracker;

@Component
public class Service {

    public void doSomething(){
        MessageTracker.addTrack("doSomething");
    }
}
