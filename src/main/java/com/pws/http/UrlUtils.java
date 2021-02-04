package com.pws.http;

import org.apache.commons.lang3.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author wilson.pan
 * @date 2021/1/14
 */

public class UrlUtils {
    public static String getBaseUrl(String location) {
        try {
            return getBaseUrl(new URL(location));
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public static String getBaseUrl(URL url) {
        if(url == null) {
            return null;
        }
        boolean hasPort = (url.getPort() != -1) && (url.getPort() != url.getDefaultPort());
        return hasPort ? (url.getProtocol() + "://" + url.getHost() + ":" + url.getPort()) : url.getProtocol() + "://" + url.getHost();
    }

    public static String getPath(String location) {
        try {
            URL url = new URL(location);
            return url.getPath();
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public static String getHost(String location) {
        try {
            URL url = new URL(location);
            return url.getHost();
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public static boolean isValidUrl(String location) {
        if(StringUtils.isEmpty(location)) {
            return false;
        }
        try {
            return StringUtils.isNotEmpty(new URL(location).getHost());
        } catch (Exception e) {
        }
        return false;
    }
}