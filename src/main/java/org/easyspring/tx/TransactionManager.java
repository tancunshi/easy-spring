package org.easyspring.tx;

import org.aopalliance.intercept.MethodInvocation;
import org.easyspring.util.MessageTracker;

public class TransactionManager {
    public void start(){
        MessageTracker.addTrack("start tx");
    }

    public void commit(){
        MessageTracker.addTrack("commit tx");
    }

    public void rollback(){
        MessageTracker.addTrack("rollback tx");
    }

    public void after(){
        MessageTracker.addTrack("after tx");
    }

    public Object around(MethodInvocation methodInvocation) throws Throwable {
        MessageTracker.addTrack("around before tx");
        Object object = methodInvocation.proceed();
        MessageTracker.addTrack("around after tx");
        return object;
    }
}
