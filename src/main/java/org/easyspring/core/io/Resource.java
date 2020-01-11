package org.easyspring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Resource用于封装被加载的资源来源
 *
 * @author tancunshi
 */
public interface Resource {
    InputStream getInputStream() throws IOException;

    String getDescription();
}
