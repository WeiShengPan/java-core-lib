package com.pws.javafeatures.filescan;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.internal.util.UrlUtils;
import org.springframework.core.io.ClassPathResource;

import java.net.URL;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author wilson.pan
 * @date 2020/11/5
 */
@Slf4j
public class FileScanner {

    public static void main(String[] args) {
        FileScanner scanner = new FileScanner();
        Set<String> resourceNames = scanner.findResourceNames("io");
        log.info("resourceNames: {}", resourceNames);
    }

    public Set<String> findResourceNames(String location) {
        Set<String> resourceNames = new TreeSet<>();

        try {
            ClassPathResource resource = new ClassPathResource(location);
            URL resolvedUrl = resource.getURL();
            log.info("ScriptUtils Scanning resources from URL: [{}]", resolvedUrl.toExternalForm());

            String protocol = resolvedUrl.getProtocol();
            ClassPathScanner scanner = createClassPathScanner(protocol);
            if (scanner == null) {
                String scanRoot = UrlUtils.toFilePath(resolvedUrl);
                log.warn("Unable to scan location: {}, (unsupported protocol: {})", scanRoot, protocol);
            } else {
                resourceNames = scanner.scan(resolvedUrl, location);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        if (resourceNames.isEmpty()) {
            log.warn("Unable to resolve location {}", location);
        }
        return resourceNames;
    }

    private ClassPathScanner createClassPathScanner(String protocol) {
        if ("file".equals(protocol)) {
            return new FileSystemClassPathScanner();
        }

        if ("jar".equals(protocol)) {
            return new JarFileClassPathScanner();
        }

        return null;
    }
}
