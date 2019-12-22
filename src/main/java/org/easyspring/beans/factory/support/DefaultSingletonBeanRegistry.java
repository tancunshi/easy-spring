package org.easyspring.beans.factory.support;

import org.easyspring.beans.factory.config.SingletonBeanRegistry;
import org.easyspring.util.Assert;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private final Map<String,Object> singletonObjects = new ConcurrentHashMap<String, Object>(64);

    public void registerSingletonBean(String beanId, Object singletonObject) {
        Assert.notNull(beanId,"'beanId' must not be null");
        Object oldObject = null;
        if ((oldObject = this.getSingletonBean(beanId)) != null){
            throw new IllegalStateException("Could not register object [" + singletonObject +
                    "] under bean name '" + beanId + "': there is already object [" + oldObject + "] bound");
        }
        this.singletonObjects.put(beanId,singletonObject);
    }

    public Object getSingletonBean(String beanId) {
        return this.singletonObjects.get(beanId);
    }
}
