package org.easyspring.core.io;

import org.easyspring.util.Assert;

import java.io.*;

public class FileSystemResource implements Resource{
    private String path;
    private File file;

    public FileSystemResource(String path){
        Assert.notNull(path, "Path must not be null");
        this.path = path;
        this.file = new File(path);
    }

    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this.file);
    }

    public String getDescription() {
        return "file [" + this.file.getAbsolutePath() + "]";
    }
}
