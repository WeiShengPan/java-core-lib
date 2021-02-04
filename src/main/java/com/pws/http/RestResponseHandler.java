package com.pws.http;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author wilson.pan
 * @date 2021/1/14
 */
public class RestResponseHandler implements ResponseHandler<RestResponse> {
    private final boolean extractHeader;

    public RestResponseHandler(boolean extractHeader) {
        this.extractHeader = extractHeader;
    }

    @Override
    public RestResponse handleResponse(HttpResponse response) throws IOException {
        int status = response.getStatusLine().getStatusCode();
        HttpEntity entity = response.getEntity();
        String responseBody = (entity != null) ? EntityUtils.toString(entity) : null;
        RestResponse restResponse = new RestResponse(status, responseBody);

        if (extractHeader) {
            for (Header header : response.getAllHeaders()) {
                restResponse.addHeader(header.getName(), header.getValue());
            }
        }
        return restResponse;
    }
}
