package com.pws.javafeatures.filescan;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Set;
import java.util.TreeSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author wilson.pan
 * @date 2020/11/5
 */
@Slf4j
public class JarFileClassPathScanner implements ClassPathScanner {

    @Override
    public Set<String> scan(URL locationURL, String location) {
        JarFile jarFile;
        try {
            jarFile = getJarFromUrl(locationURL);
        } catch (IOException e) {
            log.warn("Unable to determine jar from url ({}): {}", locationURL, e.getMessage());
            return Collections.emptySet();
        }

        try {
            return findResourceNamesFromJarFile(jarFile, location);
        } finally {
            try {
                if (jarFile != null) {
                    jarFile.close();
                }
            } catch (IOException e) {
                log.warn(e.getMessage(), e);
            }
        }
    }

    private JarFile getJarFromUrl(URL locationUrl) throws IOException {
        URLConnection con = locationUrl.openConnection();
        if (con instanceof JarURLConnection) {
            JarURLConnection jarCon = (JarURLConnection) con;
            jarCon.setUseCaches(false);
            return jarCon.getJarFile();
        }
        return null;
    }

    private Set<String> findResourceNamesFromJarFile(JarFile jarFile, String location) {
        String toScan = location + (location.endsWith("/") ? "" : "/");
        Set<String> resourceNames = new TreeSet<>();

        if (jarFile != null) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                String entryName = entries.nextElement().getName();
                if (StringUtils.startsWith(entryName, toScan) && !StringUtils.equals(entryName, toScan)) {
                    resourceNames.add(entryName);
                }
            }
        }

        return resourceNames;
    }
}
