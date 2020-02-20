package org.easyspring.aop.config;

import org.dom4j.Element;
import org.easyspring.aop.aspectj.*;
import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.PropertyValue;
import org.easyspring.beans.factory.config.RuntimeBeanReference;
import org.easyspring.beans.factory.support.BeanDefinitionReaderUtils;
import org.easyspring.beans.factory.support.BeanDefinitionRegistry;
import org.easyspring.beans.factory.support.GenericBeanDefinition;
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
        List<Element> elements = ele.elements();
        for (Element element: elements){
            String localName = element.getName();
            if (ASPECT.equals(localName)){
                //如果是Aspect元素节点
                this.parseAspect(element,registry);
            }
        }
    }

    /**
     * 解析Aspect，创建advice的BeanDefinition
     */
    private void parseAspect(Element aspectElement, BeanDefinitionRegistry registry){

        //aspectId 一般情况是不存在的
        String aspectId = aspectElement.attributeValue(ID);
        String aspectName = aspectElement.attributeValue(REF);

        List<BeanDefinition> beanDefinitions = new ArrayList<BeanDefinition>();
        List<RuntimeBeanReference> beanReferences = new ArrayList<RuntimeBeanReference>();
        beanReferences.add(new RuntimeBeanReference(aspectName));

        List<Element> elements = aspectElement.elements();
        //获得pointcut，advice元素节点
        for (int i = 0; i < elements.size(); i++){
            Element element = elements.get(i);
            if (isAdviceNode(element)){
                if (this.isAdviceNode(element)){
                    GenericBeanDefinition advisorDefinition =
                            this.parseAdvice(aspectName, i, aspectElement, element, registry, beanDefinitions, beanReferences);
                    beanDefinitions.add(advisorDefinition);
                }
            }
        }

        List<Element> pointcuts = aspectElement.elements(POINTCUT);
        for (Element pointcutElement: pointcuts){
            this.parsePointcut(pointcutElement, registry);
        }
    }

    private GenericBeanDefinition parseAdvice(
            String aspectName, int order, Element aspectElement, Element adviceElement,
            BeanDefinitionRegistry registry, List<BeanDefinition> beanDefinitions,
            List<RuntimeBeanReference> beanReferences) {

        //MethodLocatingFactory 用于定位并获得Method
        GenericBeanDefinition methodFactoryDef = new GenericBeanDefinition(MethodLocatingFactory.class);
        methodFactoryDef.getPropertyValues().add(new PropertyValue("targetBeanName", aspectName));
        methodFactoryDef.getPropertyValues().add(new PropertyValue("methodName",adviceElement.attributeValue("method")));
        methodFactoryDef.setSynthetic(true);

        //AspectInstanceFactory 从IOC容器中获得Aspect
        GenericBeanDefinition aspectFactoryDef = new GenericBeanDefinition(AspectInstanceFactory.class);
        aspectFactoryDef.getPropertyValues().add(new PropertyValue("aspectBeanName", aspectName));
        aspectFactoryDef.setSynthetic(true);

        GenericBeanDefinition adviceDef = this.createAdviceDefinition(
                adviceElement, registry, aspectName, order, methodFactoryDef, aspectFactoryDef,
                beanDefinitions, beanReferences );
        adviceDef.setSynthetic(true);

        BeanDefinitionReaderUtils.registerWithGeneratedName(adviceDef, registry);
        return adviceDef;
    }

    private GenericBeanDefinition createAdviceDefinition(
            Element adviceElement, BeanDefinitionRegistry registry, String aspectName,
            int order, GenericBeanDefinition methodFactoryDef, GenericBeanDefinition aspectFactoryDef,
            List<BeanDefinition> beanDefinitions, List<RuntimeBeanReference> beanReferences) {
        GenericBeanDefinition adviceDefinition = new GenericBeanDefinition(this.getAdviceClass(adviceElement));

        return null;
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


}
