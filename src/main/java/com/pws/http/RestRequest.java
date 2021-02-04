package com.pws.http;


import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class RestRequest {
    public static final String CONTENT_TYPE = "content-type";
    public static final String ACCEPT = "accept";

    private HttpMethod httpMethod;
    private String requestUri;
    private List<NameValuePair> headers = new ArrayList<>();
    private List<NameValuePair> params = new ArrayList<>();
    private String requestBody;

    private boolean extractHeaders = false;

    public RestRequest(HttpMethod httpMethod, String requestUri) {
        this.httpMethod = httpMethod;
        this.requestUri = requestUri;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void addHeader(String name, String value) {
        if (StringUtils.isEmpty(name)) {
            return;
        }
        this.headers.add(new BasicNameValuePair(name, value));
    }

    public void setHeader(String name, String value) {
        updateNamePair(headers, name, value);
    }

    public void setAccept(String value) {
        setHeader(ACCEPT, value);
    }

    public void setContentType(String value) {
        setHeader(CONTENT_TYPE, value);
    }

    public List<NameValuePair> getHeaders() {
        return headers;
    }

    public void addParam(String name, String value) {
        if (StringUtils.isEmpty(name)) {
            return;
        }
        this.params.add(new BasicNameValuePair(name, value));
    }

    public List<NameValuePair> getParams() {
        return params;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    private void updateNamePair(List<NameValuePair> values, String name, String value) {
        if (StringUtils.isEmpty(name)) {
            return;
        }

        for (int i = 0; i < values.size(); i++) {
            final NameValuePair current = values.get(i);
            if (current.getName().equalsIgnoreCase(name)) {
                values.set(i, new BasicNameValuePair(name, value));
                return;
            }
        }
        values.add(new BasicNameValuePair(name, value));
    }

    public boolean isExtractHeaders() {
        return extractHeaders;
    }

    public void setExtractHeaders(boolean extractHeaders) {
        this.extractHeaders = extractHeaders;
    }

    public boolean isUrlValid() {
        return UrlUtils.isValidUrl(requestUri);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("RestRequest[")
                .append("   httpMethod = ").append(httpMethod).append(",")
                .append("   requestUri = ").append(requestUri).append(",")
                .append("   extractHeaders = ").append(extractHeaders).append(",")
                .append("   requestBody = ").append(requestBody).append(",")
                .append("   headers = [")
                .append("]");

        toString(sb, headers);

        sb.append("   params = [");
        toString(sb, params);
        sb.append("]")
                .append("]");

        return sb.toString();
    }

    private void toString(StringBuilder sb, List<NameValuePair> pairs) {
        pairs.forEach(vp -> sb
                .append("name=")
                .append(vp.getName())
                .append(", value=")
                .append(vp.getValue())
                .append(";"));
    }
}
