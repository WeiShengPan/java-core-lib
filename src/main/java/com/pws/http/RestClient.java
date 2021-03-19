package com.pws.http;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.DisposableBean;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;

public class RestClient implements DisposableBean {

    private final int socketTimeout;
    private final int connectionTimeout;
    private final int maxConnection;

    private final boolean skipSSLVerification;

    private final CloseableHttpClient httpClient;

    public RestClient(int socketTimeout,
                      int connectionTimeout,
                      int maxConnection,
                      boolean skipSSLVerification)
            throws HttpRequestException {

        this.socketTimeout = socketTimeout;
        this.connectionTimeout = connectionTimeout;
        this.maxConnection = maxConnection;
        this.skipSSLVerification = skipSSLVerification;

        try {
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setConnectionManager(buildConnectionFactory())
                    .setDefaultRequestConfig(getDefaultRequestConfig())
                    .build();

            this.httpClient = httpclient;
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException | IOException e) {
            throw new HttpRequestException("Failed to initialize RestClient.", e);
        }
    }


    public RestResponse executeRequest(RestRequest request) throws HttpRequestException {
        HttpUriRequest httpRequest = buildHttpRequest(request);
        if (!request.isUrlValid()) {
            throw new HttpRequestException(String.format("URL [%s] is not a valid value.", request.getRequestUri()));
        }
        try {
//            return httpClient.execute(httpRequest, new RestResponseHandler(request.isExtractHeaders()));
            return httpClient.execute(httpRequest, new RestResponseHandler(true));
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    private HttpUriRequest buildHttpRequest(RestRequest request) throws HttpRequestException {
        HttpUriRequest httpRequest = null;

        try {
            URIBuilder builder = new URIBuilder(request.getRequestUri());
            request.getParams().forEach(pair -> {
                builder.setParameter(pair.getName(), pair.getValue());
            });
            switch (request.getHttpMethod()) {
                case GET:
                    httpRequest = new HttpGet(builder.build());
                    break;
                case POST:
                    HttpPost httpPost = new HttpPost(builder.build());
                    httpPost.setEntity(buildEntity(request));
                    httpRequest = httpPost;
                    break;
                case PUT:
                    HttpPut httpPut = new HttpPut(builder.build());
                    httpPut.setEntity(buildEntity(request));
                    httpRequest = httpPut;
                    break;
                case DELETE:
                    httpRequest = new HttpDelete(builder.build());
                    break;
                default:
                    break;
            }
        } catch (URISyntaxException | UnsupportedEncodingException e) {
            throw new HttpRequestException(String.format("Failed to build http request from parameter: [%s]", request), e);
        }

        if (httpRequest == null) {
            throw new HttpRequestException(String.format("Http request method: [%s] is not supported.", request.getHttpMethod()));
        }

        for (NameValuePair header : request.getHeaders()) {
            httpRequest.setHeader(header.getName(), header.getValue());
        }

        return httpRequest;
    }

    private HttpEntity buildEntity(RestRequest request) throws UnsupportedEncodingException {
        if (StringUtils.isNotEmpty(request.getRequestBody())) {
            return new StringEntity(request.getRequestBody());
        } else {
            return new UrlEncodedFormEntity(
                    request.getParams().stream().map(pair -> {
                        return new BasicNameValuePair(pair.getName(), pair.getValue());
                    }).collect(Collectors.toList())
            );
        }
    }

    private PoolingHttpClientConnectionManager buildConnectionFactory()
            throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {

        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", buildSocketFactory(buildSSLContext()))
                .build();

        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        connManager.setMaxTotal(this.maxConnection);
        connManager.setDefaultMaxPerRoute(this.maxConnection);

        return connManager;
    }


    private SSLConnectionSocketFactory buildSocketFactory(SSLContext sslContext) throws IOException {
        HostnameVerifier hostnameVerifier;
        if (skipSSLVerification) {
            hostnameVerifier = NoopHostnameVerifier.INSTANCE;
        } else {
            PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.load(RestClient.class.getClassLoader().getResource("http/effective_tld_names.dat"));
            hostnameVerifier = new DefaultHostnameVerifier(publicSuffixMatcher);
        }

        return new SSLConnectionSocketFactory(
                sslContext,
                new String[]{"TLSv1.2"},
                null,
                hostnameVerifier);
    }

    private SSLContext buildSSLContext() throws KeyManagementException, NoSuchAlgorithmException, IOException, KeyStoreException {
        TrustStrategy trustStrategy = null;
        if (skipSSLVerification) {
            trustStrategy = TrustAllStrategy.INSTANCE;
        }

        return SSLContexts.custom()
                .loadTrustMaterial(null, trustStrategy)
                .build();
    }

    private RequestConfig getDefaultRequestConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(connectionTimeout)
                .setConnectionRequestTimeout(connectionTimeout)
                .setSocketTimeout(socketTimeout)
                .build();
    }

    @Override
    public void destroy() throws Exception {
        httpClient.close();
    }
}
