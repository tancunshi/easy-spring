package org.easyspring.core.type;

/**
 * @author tancunshi
 */
public interface ClassMetaData {
    String getClassName();
    boolean isInterface();
    boolean isAbstract();
    boolean isFinal();
    String getSuperClassName();
    String[] getInterfaceNames();
}
