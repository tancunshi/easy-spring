package org.easyspring.test.aop;

import org.easyspring.stereotype.Component;

@Component
public class Service {

    public void placeOrder(){
        System.out.println("place order");
    }
}
