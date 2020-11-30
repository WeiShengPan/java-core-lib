package com.pws.javafeatures.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @author panws
 * @since 2017-08-31
 */
@Slf4j
@Deprecated
public class PrintUtil {

    private PrintUtil() {

    }

    private static final String LINE_SEPARATOR = "********************";

    public static void println() {
        log.info("");
    }

    public static void println(Object obj) {
        log.info(obj.toString());
    }

    public static void println(String format, Object... arg) {
        log.info(String.format(format, arg));
    }

    public static void print(Object obj) {
        log.info(obj.toString());
    }

    public static void err(Object obj) {
        log.error(obj.toString());
    }

    public static void err(String format, Object... arg) {
        log.error(String.format(format, arg));
    }

    public static void printSep() {
        log.info(LINE_SEPARATOR);
    }
}
