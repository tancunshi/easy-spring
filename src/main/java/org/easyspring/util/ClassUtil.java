package org.easyspring.util;

/**
 * @author tancunshi
 */
public class ClassUtil {
    public static ClassLoader getDefaultClassLoader(){
        ClassLoader cl = null;
        try{
            cl = cl = Thread.currentThread().getContextClassLoader();
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
