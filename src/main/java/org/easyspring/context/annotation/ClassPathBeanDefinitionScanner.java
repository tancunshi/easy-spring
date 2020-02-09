package org.easyspring.context.annotation;

import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.factory.BeanDefinitionStoreException;
import org.easyspring.beans.factory.support.BeanDefinitionRegistry;
import org.easyspring.core.annotation.AnnotationAttributes;
import org.easyspring.core.io.Resource;
import org.easyspring.core.io.support.PackageResourceLoader;
import org.easyspring.core.type.AnnotationMetaData;
import org.easyspring.core.type.classreading.MetaDataReader;
import org.easyspring.core.type.classreading.SimpleMetaDataReader;
import org.easyspring.stereotype.Component;
import org.easyspring.util.ClassUtils;
import org.easyspring.util.StringUtils;

import java.beans.Introspector;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author tancunshi
 */
public class ClassPathBeanDefinitionScanner {

    private final BeanDefinitionRegistry registry;
    private final PackageResourceLoader resourceLoader = new PackageResourceLoader();

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

    private Set<BeanDefinition> findCandidateComponents(String basePackage){
        //Candidate，候选人；从包下找出被@Component修饰的class，并转换成BeanDefinition
        Set<BeanDefinition> candidates = new LinkedHashSet<BeanDefinition>();
        try {
            Resource[] resources = this.resourceLoader.getResources(basePackage);
            for (Resource resource : resources){
                MetaDataReader reader = new SimpleMetaDataReader(resource);
                try {
                    AnnotationMetaData metaData = reader.getAnnotationMetaData();

                    if (metaData.hasAnnotation(Component.class.getName())){
                        candidates.add(this.createComponentBeanDefinition(metaData));
                    }

                }
                catch (Throwable ex){
                    throw new BeanDefinitionStoreException(
                            "Failed to read candidate component class: " + resource, ex);
                }
            }
        }
        catch (IOException e){
            throw new BeanDefinitionStoreException("I/O failure during classpath scanning", e);
        }
        return candidates;
    }

    private BeanDefinition createComponentBeanDefinition(AnnotationMetaData metaData){
        AnnotationAttributes attributes = metaData.getAnnotationAttributes(Component.class.getName());

        BeanDefinition beanDefinition = new ScannedGenericBeanDefinition(metaData);
        String beanClassName = this.buildDefaultBeanName(beanDefinition);
        if (attributes.containsKey("value")){
            beanClassName = attributes.getString("value");
        }
        beanDefinition.setId(beanClassName);
        return beanDefinition;
    }

    private String buildDefaultBeanName(BeanDefinition definition) {
        String shortClassName = ClassUtils.getShortName(definition.getBeanClassName());
        return Introspector.decapitalize(shortClassName);
    }
}
