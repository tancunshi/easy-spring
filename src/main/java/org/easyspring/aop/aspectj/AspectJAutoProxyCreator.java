package org.easyspring.aop.aspectj;

import org.easyspring.aop.Advice;
import org.easyspring.aop.MethodMatcher;
import org.easyspring.aop.Pointcut;
import org.easyspring.aop.framework.*;
import org.easyspring.beans.BeansException;
import org.easyspring.beans.factory.BeanFactory;
import org.easyspring.beans.factory.config.BeanPostProcessor;
import org.easyspring.beans.factory.config.ConfigurableBeanFactory;
import org.easyspring.tx.TransactionManager;
import org.easyspring.util.ClassUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class AspectJAutoProxyCreator implements BeanPostProcessor {

    BeanFactory beanFactory;

    @Override
    public Object beforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object afterInitialization(Object bean, String beanName) throws BeansException {
        //如果这个Bean本身就是Advice及其子类，那就不要再生成动态代理了。
        if(this.isInfrastructureClass(bean.getClass())){
            return bean;
        }

        List<Advice> advices = this.getCandidateAdvices(bean);
        if (advices.isEmpty()){
            //无需代理
            return bean;
        }

        return createProxy(advices,bean);
    }


    private Object createProxy(List<Advice> advices, Object bean) {
        AopConfig config = new AopConfigSupport();
        for (Advice advice: advices){
            config.addAdvice(advice);
        }
        Set<Class> targetInterfaces = ClassUtils.getAllInterfacesForClassAsSet(bean.getClass());
        for (Class<?> targetInterface: targetInterfaces){
            config.addInterface(targetInterface);
        }
        config.setTargetObject(bean);
        AopProxyFactory proxyFactory = null;
        if (config.getProxiedInterfaces().length == 0){
            proxyFactory = new CglibProxyFactory(config);
        }
        else {
            proxyFactory = new JdkProxyFactory(config);
        }

        return proxyFactory.getProxy();
    }

    private List<Advice> getCandidateAdvices(Object bean) {
        List<Object> advices = this.beanFactory.getBeansByType(Advice.class);

        List<Advice> result = new ArrayList<Advice>();
        for(Object o : advices){
            Pointcut pc = ((Advice) o).getPointcut();
            if(canApply(pc,bean.getClass())){
                result.add((Advice) o);
            }
        }
        return result;
    }

    public static boolean canApply(Pointcut pc, Class<?> targetClass) {


        MethodMatcher methodMatcher = pc.getMethodMatcher();

        Set<Class> classes = new LinkedHashSet<Class>(ClassUtils.getAllInterfacesForClassAsSet(targetClass));
        classes.add(targetClass);
        for (Class<?> clazz : classes) {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (methodMatcher.matches(method)) {
                    return true;
                }
            }
        }

        return false;
    }

    protected boolean isInfrastructureClass(Class<?> beanClass) {
        boolean retVal = Advice.class.isAssignableFrom(beanClass);
        return retVal;
    }

    public void setBeanFactory(ConfigurableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void print(String text){
        try {
            File file = new File("D:/printf.txt");
            FileOutputStream outputStream = new FileOutputStream(file,true);
            try {
                outputStream.write(text.getBytes());
                outputStream.write("\r\n".getBytes());
            }
            finally {
                outputStream.close();
            }
        }
        catch (Exception e){

        }
    }
}
