package org.easyspring.core.io;

import org.easyspring.util.ClassUtil;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author tancunshi
 */
public class ClassPathResource implements Resource {
    private ClassLoader classLoader;
    private String path;

    public ClassPathResource(String path){
        this(path,(ClassLoader) null);
    }

    public ClassPathResource(String path, ClassLoader classLoader){
        this.path = path;
        this.classLoader = (classLoader != null ? classLoader : ClassUtil.getDefaultClassLoader());
    }

    public InputStream getInputStream() throws IOException {
        InputStream is = classLoader.getResourceAsStream(path);
        if (is == null){
            throw new FileNotFoundException(path + " cannot be opened");
        }
        return is;
    }

    public String getDescription() {
        return this.path;
    }
}
