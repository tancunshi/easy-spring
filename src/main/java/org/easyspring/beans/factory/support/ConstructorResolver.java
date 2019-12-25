package org.easyspring.beans.factory.support;

import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.ConstructorArgument;
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
    private final BeanFactory factory;

    public ConstructorResolver(BeanFactory factory) {
        this.factory = factory;
    }

    public Object autowireConstructor(BeanDefinition bd) {
        try{
            Class<?> clazz = Class.forName(bd.getBeanClassName());
            List<ValueHolder> vhs = bd.getConstructorArgument().getArgumentValues();
            Object[] consArgs = this.parseToConstructorParam(vhs);
            Constructor constructor = this.findConstructor(clazz,vhs);

            return constructor.newInstance(consArgs);
        }
        catch (Exception e){
            throw new RuntimeException("获取构造器失败");
        }
    }

    public Constructor findConstructor(Class clazz,List<ValueHolder> vhs){
        return clazz.getConstructors()[1];
    }

    public Object[] parseToConstructorParam(List<ValueHolder> vhs){
        Object[] params = new Object[vhs.size()];
        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver((DefaultBeanFactory) factory);
        for (int i = 0; i < vhs.size(); i++){
            ValueHolder vh = vhs.get(i);
            params[i] = resolver.resolveValueIfNecessary(vh.getValue());
        }
        return params;
    }
}
