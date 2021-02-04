package com.pws.http;

public class HttpRequestException extends Exception {

    public HttpRequestException() {
        super();
    }

    public HttpRequestException(String message) {
        super(message);
    }

    public HttpRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpRequestException(Throwable cause) {
        super(cause);
    }

}
