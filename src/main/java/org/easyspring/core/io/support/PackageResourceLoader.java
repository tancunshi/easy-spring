package org.easyspring.core.io.support;

import org.easyspring.core.io.FileSystemResource;
import org.easyspring.core.io.Resource;
import org.easyspring.util.ClassUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author tancunshi
 */
public class PackageResourceLoader {
    private ClassLoader classLoader;

    public PackageResourceLoader() {
    }

    public PackageResourceLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public Resource[] getResources(String basePackage) throws IOException {
        try {
            String location = ClassUtils.convertClassPathToResourcePath(basePackage);
            ClassLoader cl = getClassLoader();
            URL url = cl.getResource(location);
            File rootDir = new File(url.getFile());

            Set<File> matchingFiles = retrieveMatchingFiles(rootDir);
            Resource[] resources = new Resource[matchingFiles.size()];
            int i = 0;
            for (File file : matchingFiles) {
                resources[i++] = new FileSystemResource(file);
            }

            return resources;
        } catch (Exception e) {
            throw new IOException("Fail to get resource of package");
        }
    }

    public Set<File> retrieveMatchingFiles(File rootDir) {

        if (!rootDir.exists()) {
            return Collections.emptySet();
        }

        if (!rootDir.isDirectory()) {
            return Collections.emptySet();
        }

        if (!rootDir.canRead()) {
            return Collections.emptySet();
        }

        Set<File> result = new LinkedHashSet<File>(8);
        this.retrieveMatchingFiles(rootDir, result);
        return result;
    }

    public void retrieveMatchingFiles(File dir, Set<File> set) {

        File[] files = dir.listFiles();

        for (File file : files) {
            if (!file.isDirectory()) {
                set.add(file);
            } else {
                retrieveMatchingFiles(file, set);
            }
        }
    }

    private ClassLoader getClassLoader() {
        if (this.classLoader == null) {
            this.classLoader = ClassUtils.getDefaultClassLoader();
        }
        return this.classLoader;
    }
}
