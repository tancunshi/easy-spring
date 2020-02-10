package org.easyspring.beans.factory.annotation;

import java.util.LinkedList;
import java.util.List;

/**
 * @author tancunshi
 */
public class InjectionMetadata implements InjectionElement{

    private List<InjectionElement> injectionElements;
    public InjectionMetadata(LinkedList<InjectionElement> injectionElements) {
        this.injectionElements = injectionElements;
    }

    public List<InjectionElement> getInjectionElements(){
        return this.injectionElements;
    }

    public void inject(Object target) {
        if (injectionElements == null || injectionElements.isEmpty()){
            return;
        }
        for (InjectionElement ele : injectionElements){
            ele.inject(target);
        }
    }
}
