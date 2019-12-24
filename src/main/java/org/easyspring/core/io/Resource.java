package org.easyspring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author tancunshi
 */
public interface Resource {
    InputStream getInputStream() throws IOException;
    String getDescription();
}
