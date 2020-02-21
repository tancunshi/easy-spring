package org.easyspring.aop.config;

import org.easyspring.beans.factory.BeanFactory;
import org.easyspring.beans.factory.BeanFactoryAware;
import org.easyspring.util.StringUtils;

public class AspectInstanceFactory implements BeanFactoryAware {

    private String aspectBeanId;
    private BeanFactory beanFactory;

    public void setAspectBeanId(String aspectBeanId) {
        this.aspectBeanId = aspectBeanId;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        if (!StringUtils.hasText(this.aspectBeanId)) {
            throw new IllegalArgumentException("'aspectBeanName' is required");
        }
    }

    public Object getAspectInstance(){
        return this.beanFactory.getBean(this.aspectBeanId);
    }
}
