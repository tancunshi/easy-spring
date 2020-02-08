package org.easyspring.core.type.classreading;

import org.easyspring.core.type.ClassMetaData;
import org.easyspring.core.type.asm.AbstractClassVisitor;
import org.easyspring.util.ClassUtils;
import org.springframework.asm.*;

/**
 * ClassMetadataReadingVisitor扮演两种角色，一种是观察者，一种是元数据容器
 * 为了将者两种角色分开，需要不同的接口
 * @author tancunshi
 */
public class ClassMetadataReadingVisitor extends AbstractClassVisitor implements ClassMetaData {
    private String className;
    private boolean isInterface;
    private boolean isAbstract;
    private boolean isFinal;
    private String superClassName;
    private String[] interfaces;

    public ClassMetadataReadingVisitor(){

    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.className = ClassUtils.convertResourcePathToClassName(name);
        this.isInterface = ((access & Opcodes.ACC_INTERFACE) != 0);
        this.isAbstract = ((access & Opcodes.ACC_ABSTRACT) != 0);
        this.isFinal = ((access & Opcodes.ACC_FINAL) != 0);
        this.superClassName = ClassUtils.convertResourcePathToClassName(superName);
        this.interfaces = new String[interfaces.length];
        for (int i = 0; i < interfaces.length; i++){
            this.interfaces[i] = ClassUtils.convertResourcePathToClassName(interfaces[i]);
        }
    }

    public String getClassName() {
        return className;
    }

    public boolean isInterface() {
        return isInterface;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public String getSuperClassName() {
        return superClassName;
    }

    public String[] getInterfaceNames() {
        return interfaces;
    }
}
