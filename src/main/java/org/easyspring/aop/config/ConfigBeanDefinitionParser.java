package org.easyspring.aop.config;

import org.dom4j.Element;
import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.factory.config.RuntimeBeanReference;
import org.easyspring.beans.factory.support.BeanDefinitionRegistry;
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

    private void parseAspect(Element aspectElement, BeanDefinitionRegistry registry){
       String aspectId = aspectElement.attributeValue(ID);
       String aspectName = aspectElement.attributeValue(REF);

       List<BeanDefinition> beanDefinitions = new ArrayList<BeanDefinition>();
       List<RuntimeBeanReference> beanReferences = new ArrayList<RuntimeBeanReference>();

       //获得pointcut，advice元素节点
       List<Element> elements = aspectElement.elements();
       boolean adviceFoundAlready = false;
       for (int i = 0; i < elements.size(); i++){
           Element element = elements.get(i);
           if (this.isAdviceNode(element)){

           }
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


}
