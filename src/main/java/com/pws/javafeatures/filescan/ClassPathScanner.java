package com.pws.javafeatures.filescan;

import java.net.URL;
import java.util.Set;

public interface ClassPathScanner {

    Set<String> scan(URL locationURL, String location);
}
