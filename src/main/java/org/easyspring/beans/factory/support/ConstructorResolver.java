package org.easyspring.beans.factory.support;

import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.ConstructorArgument;
import org.easyspring.beans.SimpleTypeConverter;
import org.easyspring.beans.factory.BeanCreationException;
import org.easyspring.beans.factory.BeanFactory;

import java.lang.reflect.Constructor;

import org.easyspring.beans.ConstructorArgument.ValueHolder;
import org.easyspring.beans.factory.config.ConfigurableBeanFactory;

import java.util.List;

/**
 * 这个类用于筛选出用于构建bd所描述的bean的构造函数
 *
 * @author tancunshi
 */
public class ConstructorResolver {
    private final DefaultBeanFactory factory;

    public ConstructorResolver(DefaultBeanFactory factory) {
        this.factory = factory;
    }

    public Object autowireConstructor(final BeanDefinition bd) {
        Constructor<?> constructorToUse = null;
        Class<?> clazz = null;
        Object[] argsToUse = null;

        ClassLoader cl = factory.getClassLoader();
        try {
            clazz = cl.loadClass(bd.getBeanClassName());
        } catch (Exception e) {
            throw new BeanCreationException("Instantiation of bean failed, can't resolve class", e);
        }
        Constructor[] condidates = clazz.getConstructors();
        ConstructorArgument cags = bd.getConstructorArgument();

        for (int i = 0; i < condidates.length; i++) {
            Class<?>[] parameterTypes = condidates[i].getParameterTypes();
            if (parameterTypes.length != cags.getArgumentCount()) {
                continue;
            }

            argsToUse = new Object[parameterTypes.length];
            BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(factory);
            SimpleTypeConverter converter = new SimpleTypeConverter();
            boolean result = valueMatchType(cags.getArgumentValues(),
                    parameterTypes, resolver, converter, argsToUse);

            if (result) {
                constructorToUse = condidates[i];
            }
        }

        if (constructorToUse == null) {
            throw new BeanCreationException(bd.getID() + "can't find a apporiate constructor");
        }

        try {
            return constructorToUse.newInstance(argsToUse);
        } catch (Exception e) {
            throw new BeanCreationException(bd.getID() + "can't find a create instance using " + constructorToUse , e);
        }
    }

    public boolean valueMatchType(List<ValueHolder> valueHolders,
                                  Class<?>[] parameterTypes,
                                  BeanDefinitionValueResolver resolver,
                                  SimpleTypeConverter converter,
                                  Object[] argsToUse) {

        for (int i = 0; i < parameterTypes.length; i++) {
            Class type = parameterTypes[i];
            Object originValue = valueHolders.get(i).getValue();
            try {
                Object resolvedValue = resolver.resolveValueIfNecessary(originValue);
                Object convertedValue = converter.convertIfNecessary(resolvedValue, type);
                argsToUse[i] = convertedValue;
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
}
