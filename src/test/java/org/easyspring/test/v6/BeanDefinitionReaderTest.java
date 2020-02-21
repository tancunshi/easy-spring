package org.easyspring.test.v6;

import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.factory.support.BeanDefinitionReaderUtils;
import org.easyspring.beans.factory.support.BeanDefinitionRegistry;
import org.easyspring.beans.factory.support.DefaultBeanFactory;
import org.easyspring.beans.factory.support.GenericBeanDefinition;
import org.easyspring.test.aop.Controller;
import org.junit.Assert;
import org.junit.Test;
import java.util.HashSet;
import java.util.Set;

public class BeanDefinitionReaderTest {

    BeanDefinitionRegistry registry = new DefaultBeanFactory(){

        Set<String> set = new HashSet<String>();

        @Override
        public BeanDefinition getBeanDefinition(String beanId) {
            if (set.contains(beanId)){
                return new GenericBeanDefinition();
            }
            return null;
        }

        @Override
        public void registerBeanDefinition(String beanId, BeanDefinition bd) {
            set.add(beanId);
        }
    };

    @Test
    public void generatedBeanName(){
        String className = Controller.class.getName();
        BeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClassName(className);

        for (int i = 0; i < 5; i++){
            registry.registerBeanDefinition(className + "#" + i, null);
        }

        String expect = className + "#" + 5;
        String actual = BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition,registry);
        Assert.assertEquals(expect, actual);
    }
}
