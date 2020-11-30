package com.pws.javafeatures.filescan;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.internal.util.UrlUtils;

import java.io.File;
import java.net.URL;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author wilson.pan
 * @date 2020/11/5
 */
@Slf4j
public class FileSystemClassPathScanner implements ClassPathScanner {

    @Override
    public Set<String> scan(URL locationURL, String location) {
        String filePath = UrlUtils.toFilePath(locationURL);
        File folder = new File(filePath);
        if (!folder.isDirectory()) {
            log.debug("Skipping path as it is not a directory: {}", filePath);
            return new TreeSet<>();
        }

        String classPathRootOnDisk = filePath.substring(0, filePath.length() - location.length());
        if (!classPathRootOnDisk.endsWith(File.separator)) {
            classPathRootOnDisk = classPathRootOnDisk + File.separator;
        }

        Set<String> fileNames = new TreeSet<>();
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                fileNames.add(toClassPathResourceName(file, classPathRootOnDisk));
            }
        }
        return fileNames;
    }

    private String toClassPathResourceName(File file, String classPathRootOnDisk) {
        String fileName = file.getAbsolutePath().replace("\\", "/");
        return fileName.substring(classPathRootOnDisk.length());
    }
}
