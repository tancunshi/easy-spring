package org.easyspring.core.type.classreading;

import org.easyspring.core.io.Resource;
import org.easyspring.core.type.AnnotationMetaData;
import org.easyspring.core.type.ClassMetaData;
import org.springframework.asm.ClassReader;
import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * 提供简易的接口，封装了asm的使用细节
 * @author tancunshi
 */
public class SimpleMetaDataReader implements MetaDataReader{

    private final Resource resource;
    private final ClassMetaData classMetaData;
    private final AnnotationMetaData annotationMetaData;

    public SimpleMetaDataReader(Resource resource){
        try {
            InputStream is = new BufferedInputStream(resource.getInputStream());
            try {
                ClassReader classReader = new ClassReader(is);;

                AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();
                classReader.accept(visitor, true);

                this.annotationMetaData = visitor;
                this.classMetaData = visitor;
                this.resource = resource;
            }
            finally {
                is.close();
            }
        }
        catch (Exception e){
            throw new RuntimeException(resource.getDescription()+" 获取元数据失败");
        }
    }

    public Resource getResource() {
        return this.resource;
    }

    public ClassMetaData getClassMetadata() {
        return this.classMetaData;
    }

    public AnnotationMetaData getAnnotationMetaData() {
        return this.annotationMetaData;
    }
}
