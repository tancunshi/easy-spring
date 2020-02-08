package org.easyspring.context.annotation;

import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.factory.support.BeanDefinitionRegistry;
import org.easyspring.beans.factory.support.GenericBeanDefinition;
import org.easyspring.core.annotation.AnnotationAttributes;
import org.easyspring.core.io.Resource;
import org.easyspring.core.io.support.PackageResourceLoader;
import org.easyspring.core.type.AnnotationMetaData;
import org.easyspring.core.type.classreading.SimpleMetaDataReader;
import org.easyspring.util.ClassUtils;
import org.easyspring.util.StringUtils;
import java.util.LinkedHashSet;
import java.util.Set;

public class ClassPathBeanDefinitionScanner {

    private final BeanDefinitionRegistry registry;
    private final PackageResourceLoader resourceLoader = new PackageResourceLoader();
    private final String COMPONENT_ANNOTATION_TYPE = "org.easyspring.stereotype.Component";

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry){
        this.registry = registry;
    }

    public void doScan(String packageToScan){
        String[] basePackages = StringUtils.tokenizeToStringArray(packageToScan,",");

        for (String basePackage : basePackages){
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition candidate : candidates){
                registry.registerBeanDefinition(candidate.getID(),candidate);
            }
        }
    }

    public Set<BeanDefinition> findCandidateComponents(String basePackage){
        //Candidate，候选人；从包下找出被@Component修饰的class，并转换成BeanDefinition
        Resource[] resources = this.resourceLoader.getResources(basePackage);
        Set<BeanDefinition> bds = new LinkedHashSet<BeanDefinition>();
        for (Resource resource : resources){
            SimpleMetaDataReader reader = new SimpleMetaDataReader(resource);
            AnnotationMetaData metaData = reader.getAnnotationMetaData();
            if (!metaData.hasAnnotation(COMPONENT_ANNOTATION_TYPE)){
                continue;
            }

            AnnotationAttributes attributes = metaData.getAnnotationAttributes(COMPONENT_ANNOTATION_TYPE);

            //判断Component注解是否存在value
            String className = metaData.getClassName();
            String id = ClassUtils.getShortName(className);
            if (attributes.containsKey("value")){
                id = attributes.getString("value");
            }

            //现有的问题，bean的命名问题
            BeanDefinition bd = new GenericBeanDefinition(id,className);
            bds.add(bd);
        }
        return bds;
    }


}
