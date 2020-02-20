package org.easyspring.aop.config;

import org.easyspring.beans.factory.BeanFactory;
import org.easyspring.beans.factory.BeanFactoryAware;
import org.easyspring.util.StringUtils;

public class AspectInstanceFactory implements BeanFactoryAware {

    private String aspectBeanName;
    private BeanFactory beanFactory;

    public void setAspectBeanName(String aspectBeanName){
        this.aspectBeanName = aspectBeanName;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        if (!StringUtils.hasText(this.aspectBeanName)) {
            throw new IllegalArgumentException("'aspectBeanName' is required");
        }
    }

    public Object getAspectInstance(){
        return this.beanFactory.getBean(this.aspectBeanName);
    }
}
