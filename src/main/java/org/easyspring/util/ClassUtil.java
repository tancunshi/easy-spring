package org.easyspring.util;

public abstract class ClassUtil {
    public static ClassLoader getDefaultClassLoader(){//获取系统类加载器
        ClassLoader cl = null;
        try{
            cl = cl = Thread.currentThread().getContextClassLoader();//Java默认在线程中加入系统类加载器，用于破坏双亲委派模式
        }catch (Throwable ex){

        }
        if (cl == null){
            cl = ClassUtil.class.getClassLoader();
            if (cl == null){
                try {
                    cl = ClassLoader.getSystemClassLoader();
                }catch (Throwable ex){

                }
            }
        }
        return cl;
    }
}
