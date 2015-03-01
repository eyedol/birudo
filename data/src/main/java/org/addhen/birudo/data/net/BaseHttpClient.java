/*
 * Copyright 2015 Henry Addo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.addhen.birudo.data.net;


import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.util.Base64;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

public abstract class BaseHttpClient {

    private static final int TIME_OUT_CONNECTION = 30;

    private static final String DEFAULT_ENCODING = "UTF-8";

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=" + DEFAULT_ENCODING);

    public static final MediaType XML = MediaType
            .parse("application/json; charset=" + DEFAULT_ENCODING);

    public static final MediaType YAML = MediaType
            .parse("application/xml; charset=" + DEFAULT_ENCODING);

    private static final String CLASS_TAG = BaseHttpClient.class.getSimpleName();

    protected OkHttpClient httpClient;

    protected String url;

    private Response response;

    private Request request;

    private ArrayList<NameValuePair> params;

    private Map<String, String> header;

    private Headers headers;

    private HttpMethod method = HttpMethod.GET;

    private RequestBody requestBody;

    public BaseHttpClient(String url) {

        this.url = url;
        this.params = new ArrayList<>();
        this.header = new HashMap<>();

        httpClient = new OkHttpClient();
        httpClient.setConnectTimeout(TIME_OUT_CONNECTION, TimeUnit.SECONDS);
        httpClient.setWriteTimeout(TIME_OUT_CONNECTION, TimeUnit.SECONDS);
        httpClient.setReadTimeout(TIME_OUT_CONNECTION, TimeUnit.SECONDS);
    }

    private static void debug(Exception e) {
        Timber.d(CLASS_TAG, "Exception: "
                + e.getClass().getName()
                + " " + getRootCause(e).getMessage());
    }

    public static String base64Encode(String str) {
        byte[] bytes = str.getBytes();
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    public static Throwable getRootCause(Throwable throwable) {
        if (throwable.getCause() != null) {
            return getRootCause(throwable.getCause());
        }
        return throwable;
    }

    protected void setHeader(String name, String value) {
        this.header.put(name, value);
    }

    protected void setHeaders(Headers headers) {
        this.headers = headers;
    }

    private void addHeader() {
        try {
            URI uri = new URI(url);
            String userInfo = uri.getUserInfo();
            if (userInfo != null) {
                setHeader("Authorization", "Basic " + base64Encode(userInfo));
            }
        } catch (URISyntaxException e) {
            debug(e);
        }

        StringBuilder userAgent = new StringBuilder("BirudoRogu-Android");
        setHeader("User-Agent", userAgent.toString());
        // set headers on request
        Headers.Builder headerBuilder = new Headers.Builder();
        for (String key : header.keySet()) {
            headerBuilder.set(key, header.get(key));
        }
        setHeaders(headerBuilder.build());
    }

    protected void addParam(String name, String value) {
        this.params.add(new BasicNameValuePair(name, value));
    }

    public ArrayList getParams() {
        return params;
    }

    protected void execute() throws Exception {
        prepareRequest();
        if (request != null) {
            final Response resp = httpClient.newCall(request).execute();
            setResponse(resp);
        }
    }

    protected boolean isMethodSupported(HttpMethod method) {
        return (method.equals(HttpMethod.GET) || method.equals(HttpMethod.POST) || method
                .equals(HttpMethod.PUT));
    }

    protected void setMethod(HttpMethod method) throws Exception {
        if (!isMethodSupported(method)) {
            throw new Exception(
                    "Invalid method '" + method + "'."
                            + " POST, PUT and GET currently supported."
            );
        }
        this.method = method;
    }

    protected void setRequestBody(RequestBody requestBody) throws Exception {
        this.requestBody = requestBody;
    }

    protected Request getRequest() {
        return request;
    }

    private void prepareRequest() throws Exception {
        addHeader();
        // setup parameters on request
        if (method.equals(HttpMethod.GET)) {
            request = new Request.Builder()
                    .url(url + getQueryString())
                    .headers(headers)
                    .build();
        } else if (method.equals(HttpMethod.POST)) {
            request = new Request.Builder()
                    .url(url)
                    .headers(headers)
                    .post(requestBody)
                    .build();

        } else if (method.equals(HttpMethod.PUT)) {
            request = new Request.Builder()
                    .url(url)
                    .headers(headers)
                    .put(requestBody)
                    .build();
        }
    }

    private String getQueryString() throws Exception {
        //add query parameters
        String combinedParams = "";
        if (!params.isEmpty()) {
            combinedParams += "?";
            for (NameValuePair p : params) {
                String paramString = p.getName() + "=" + URLEncoder
                        .encode(p.getValue(), DEFAULT_ENCODING);
                if (combinedParams.length() > 1) {
                    combinedParams += "&" + paramString;
                } else {
                    combinedParams += paramString;
                }
            }
        }
        return combinedParams;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    protected void log(String message) {
        Timber.d(getClass().getName(), message);
    }

    protected void log(String format, Object... args) {
        Timber.d(getClass().getName(), format, args);
    }

    protected void log(String message, Exception ex) {
        Timber.d(getClass().getName(), message, ex);
    }

    public enum HttpMethod {
        POST("POST"),
        GET("GET"),
        PUT("PUT");

        private final String mMethod;

        HttpMethod(String method) {
            mMethod = method;
        }

        public String value() {
            return mMethod;
        }
    }
}
