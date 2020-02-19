package org.easyspring.test.aop;

import org.easyspring.util.MessageTracker;

public class Bird implements Animal{
    public void eat() {
        MessageTracker.addTrack("bird eat");
    }
}
