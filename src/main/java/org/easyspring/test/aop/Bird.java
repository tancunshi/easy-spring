package org.easyspring.test.aop;

import org.easyspring.stereotype.Component;
import org.easyspring.util.MessageTracker;

@Component
public class Bird implements Animal{
    public void eat() {
        MessageTracker.addTrack("bird eat");
    }
}
