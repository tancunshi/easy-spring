package org.easyspring.tx;

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
}
