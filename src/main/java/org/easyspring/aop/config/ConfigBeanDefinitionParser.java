package org.easyspring.aop.config;

import org.dom4j.Element;
import org.easyspring.aop.aspectj.*;
import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.ConstructorArgument;
import org.easyspring.beans.PropertyValue;
import org.easyspring.beans.factory.BeanCreationException;
import org.easyspring.beans.factory.config.RuntimeBeanReference;
import org.easyspring.beans.factory.support.BeanDefinitionReaderUtils;
import org.easyspring.beans.factory.support.BeanDefinitionRegistry;
import org.easyspring.beans.factory.support.GenericBeanDefinition;
import org.easyspring.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class ConfigBeanDefinitionParser {

    private static final String ASPECT = "aspect";
    private static final String EXPRESSION = "expression";
    private static final String ID = "id";
    private static final String POINTCUT = "pointcut";
    private static final String POINTCUT_INF = "pointcut-ref";
    private static final String REF = "ref";
    private static final String BEFORE = "before";
    private static final String AFTER = "after";
    private static final String AFTER_RETURNING_ELEMENT = "after-returning";
    private static final String AFTER_THROWING_ELEMENT = "after-throwing";
    private static final String AROUND = "around";
    private static final String ASPECT_NAME_PROPERTY = "aspectName";

    public void parse(Element ele, BeanDefinitionRegistry registry) {
        //ele为元素节点 config
        List<Element> elements = ele.elements();
        for (Element element: elements){
            //如果是aspect元素节点
            if (ASPECT.equals(element.getName())){
                //解析aspect元素节点
                this.parseAspect(element,registry);
            }
        }
    }

    private void parseAspect(Element aspectElement, BeanDefinitionRegistry registry){

        List<Element> elements = aspectElement.elements();
        //获得pointcut，advice元素节点
        for (int i = 0; i < elements.size(); i++){
            Element element = elements.get(i);
            //如果是advice元素节点
            if (this.isAdviceNode(element)){
                //解析advice，并注册BeanDefinition
                this.parseAdvice(aspectElement.attributeValue(REF), element, registry);
            }
        }

        //获得aspect元素节点下的pointcut元素节点
        List<Element> pointcuts = aspectElement.elements(POINTCUT);
        for (Element pointcutElement: pointcuts){
            //解析pointcut元素节点，并对pointcutBeanDefinition进行注册
            this.parsePointcut(pointcutElement, registry);
        }
    }

    private void parseAdvice(String aspectBeanId, Element adviceElement, BeanDefinitionRegistry registry) {

        //MethodLocatingFactory 定位method，这里是用来定位aspectMethod
        GenericBeanDefinition methodFactoryDef = new GenericBeanDefinition(MethodLocatingFactory.class);
        methodFactoryDef.getPropertyValues().add(new PropertyValue("beanId", aspectBeanId));
        methodFactoryDef.getPropertyValues().add(new PropertyValue("methodName",adviceElement.attributeValue("method")));
        methodFactoryDef.setSynthetic(true);

        //AspectInstanceFactory 根据aspectBeanName获得aspectBean实例
        GenericBeanDefinition aspectFactoryDef = new GenericBeanDefinition(AspectInstanceFactory.class);
        aspectFactoryDef.getPropertyValues().add(new PropertyValue("aspectBeanId", aspectBeanId));
        aspectFactoryDef.setSynthetic(true);

        GenericBeanDefinition adviceDef = this.createAdviceDefinition(
                adviceElement, aspectBeanId, methodFactoryDef, aspectFactoryDef);
        adviceDef.setSynthetic(true);

        //注册advice BeanDefinition
        BeanDefinitionReaderUtils.registerWithGeneratedName(adviceDef, registry);
    }

    private GenericBeanDefinition createAdviceDefinition(
            Element adviceElement, String aspectName, GenericBeanDefinition methodFactoryDef,
            GenericBeanDefinition aspectFactoryDef) {
        //获得advice实现类，并创建BeanDefinition
        GenericBeanDefinition adviceDefinition = new GenericBeanDefinition(this.getAdviceClass(adviceElement));
        adviceDefinition.getPropertyValues().add(new PropertyValue(ASPECT_NAME_PROPERTY, aspectName));

        //提供两种用于创建advice的构造器
        ConstructorArgument argument = adviceDefinition.getConstructorArgument();
        argument.addArgumentValue(methodFactoryDef);
        Object pointcut = this.parsePointcutProperty(adviceElement);
        if (pointcut instanceof BeanDefinition){
            argument.addArgumentValue(pointcut);
        }
        else if (pointcut instanceof String){
            RuntimeBeanReference pointRef = new RuntimeBeanReference((String) pointcut);
            argument.addArgumentValue(pointRef);
        }
        argument.addArgumentValue(aspectFactoryDef);
        return adviceDefinition;
    }

    private Object parsePointcutProperty(Element adviceElement) {
        //advice节点中有两种方式定义pointcut，一种是指定元素节点pointcut = "* org.easyspring.test.aop.*.sayHello(..))"
        //另一种是指定元素节点point-ref="pointcutBeanId"
        if (!StringUtils.hasLength(adviceElement.attributeValue(POINTCUT)) &&
                !StringUtils.hasLength(adviceElement.attributeValue(POINTCUT_INF))) {
            return null;
        }
        else if (adviceElement.attribute(POINTCUT) != null){
            String expression = adviceElement.attributeValue(POINTCUT);
            GenericBeanDefinition pointcutDefinition = this.createPointcutDefinition(expression);
            return pointcutDefinition;
        }
        else if (adviceElement.attribute(POINTCUT_INF) != null){
            String pointcutRef = adviceElement.attributeValue(POINTCUT_INF);
            return pointcutRef;
        }else {
            return null;
        }
    }

    private Class<?> getAdviceClass(Element adviceElement) {
        String elementName = adviceElement.getName();
        if (BEFORE.equals(elementName)) {
            return AspectJBeforeAdvice.class;
        }
		else if (AFTER.equals(elementName)) {
			return AspectJAfterAdvice.class;
		}
        else if (AFTER_RETURNING_ELEMENT.equals(elementName)) {
            return AspectJAfterReturningAdvice.class;
        }
        else if (AFTER_THROWING_ELEMENT.equals(elementName)) {
            return AspectJAfterThrowingAdvice.class;
        }
		else if (AROUND.equals(elementName)) {
			return AspectJAroundAdvice.class;
		}
        else {
            throw new IllegalArgumentException("Unknown advice kind [" + elementName + "].");
        }
    }

    private void parsePointcut(Element pointcutElement, BeanDefinitionRegistry registry) {
        String id = pointcutElement.attributeValue(ID);
        String expression = pointcutElement.attributeValue(EXPRESSION);
        GenericBeanDefinition pointcutBeanDefinition = this.createPointcutDefinition(expression);;
        String pointcutBeanName = id;
        if (StringUtils.hasLength(pointcutBeanName)) {
            registry.registerBeanDefinition(pointcutBeanName, pointcutBeanDefinition);
        }
        else {
            throw new BeanCreationException("pointcut beanId not exist");
        }
    }

    private boolean isAdviceNode(Element element) {
        String elementName = element.getName();
        if (BEFORE.equals(elementName) ||
                AFTER.equals(elementName) ||
                AFTER_RETURNING_ELEMENT.equals(elementName) ||
                AFTER_THROWING_ELEMENT.equals(elementName) ||
                AROUND.equals(elementName)){
            return true;
        }
        return false;
    }

    private GenericBeanDefinition createPointcutDefinition(String expression) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition(AspectJExpressionPointcut.class);
        //设置score = protoType
        beanDefinition.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        beanDefinition.setSynthetic(true);
        beanDefinition.getPropertyValues().add(new PropertyValue(EXPRESSION, expression));
        return beanDefinition;
    }
}
