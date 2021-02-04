package com.pws.http;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wilson.pan
 * @date 2021/1/14
 */

public class RestResponse {
    private int statusCode;
    private List<NameValuePair> headers = new ArrayList<>();
    private String responseBody;

    public RestResponse(int statusCode) {
        this.statusCode = statusCode;
    }

    public RestResponse(int statusCode, String responseBody) {
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public List<NameValuePair> getHeaders() {
        return headers;
    }

    public void addHeader(String name, String value) {
        if (StringUtils.isEmpty(name)) {
            return;
        }
        this.headers.add(new BasicNameValuePair(name, value));
    }

    public String getResponseBody() {
        return responseBody;
    }

    @Override
    public String toString() {
        return "RestResponse{" +
                "statusCode=" + statusCode +
                ", responseBody='" + responseBody + '\'' +
                '}';
    }
}
