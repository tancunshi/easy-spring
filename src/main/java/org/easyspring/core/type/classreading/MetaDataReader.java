package org.easyspring.core.type.classreading;

import org.easyspring.core.io.Resource;
import org.easyspring.core.type.AnnotationMetaData;
import org.easyspring.core.type.ClassMetaData;

/**
 * 提供简易的接口，封装了asm的使用细节
 * @author tancunshi
 */
public interface MetaDataReader {
    Resource getResource();
    ClassMetaData getClassMetadata();
    AnnotationMetaData getAnnotationMetaData();
}
