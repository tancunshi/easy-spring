package org.easyspring.aop;

public class AopInvocationException extends RuntimeException {

    public AopInvocationException(String msg) {
        super(msg);
    }

    public AopInvocationException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
