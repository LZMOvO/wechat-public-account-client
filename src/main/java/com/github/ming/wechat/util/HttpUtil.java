package com.github.ming.wechat.util;

import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

/**
 * httpClient请求工具
 *
 * @author ZM
 */
public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = null;

    private static CloseableHttpClient httpClient = null;

    private static RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(2 * 1000)
            .setSocketTimeout(2 * 1000).setConnectionRequestTimeout(500).build();

    static {
        initHttpClient();
        IdleConnectionMonitorThread idleThread = new IdleConnectionMonitorThread(poolingHttpClientConnectionManager);
        idleThread.setDaemon(true);
        idleThread.start();
    }

    /**
     * 请求重试次数
     */
    private static final int EXECUTION_NUM = 0;

    /**
     * 最大连接数
     */
    private static final int MAX_TOTAL = 200;

    /**
     * 每个路由基础的连接
     */
    private static final int DEFAULT_MAX_PRR_ROUTE = 20;

    private HttpUtil() {
    }

    /**
     * 普通get请求
     *
     * @param url 请求url
     * @return
     */
    public static String get(String url) {
        HttpGet httpGet = new HttpGet(url);
        return httpResult(httpGet);
    }

    /**
     * 携带参数get请求
     *
     * @param url    请求url
     * @param params 多个参数，key=参数名，value=参数值
     * @return
     */
    public static String get(String url, Map<String, Object> params) {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setPath(url);
        uriBuilder.setParameters(params2NVPs(params));
        try {
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            return httpResult(httpGet);
        } catch (URISyntaxException e) {
            logger.error("携带参数get请求  请求出错：" + e.getMessage(), e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 携带头信息，参数的get请求
     *
     * @param url     请求url
     * @param headers
     * @param params
     * @return
     */
    public static String get(String url, Map<String, Object> headers, Map<String, Object> params) {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setPath(url);
        uriBuilder.setParameters(params2NVPs(params));
        try {
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            if (headers != null && headers.size() > 0) {
                for (Entry<String, Object> param : headers.entrySet()) {
                    httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
                }
            }
            return httpResult(httpGet);
        } catch (URISyntaxException e) {
            logger.error("携带头信息，参数的get请求   请求出错：" + e.getMessage(), e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 普通post请求
     *
     * @param url 请求url
     * @return
     */
    public static String post(String url) {
        HttpPost httpPost = new HttpPost(url);
        return httpResult(httpPost);
    }

    /**
     * 携带参数的post请求，参数编码UTF-8
     *
     * @param url    请求url
     * @param params 请求参数map集合
     * @return
     */
    public static String post(String url, Map<String, Object> params) {
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params2NVPs(params), "UTF-8"));
            return httpResult(httpPost);
        } catch (UnsupportedEncodingException e) {
            logger.error("携带参数的post请求   请求出错：" + e.getMessage(), e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 携带头信息，参数的post请求，参数编码UTF-8
     *
     * @param url     请求url
     * @param headers 请求头map结合
     * @param params  请求参数map集合
     * @return
     */
    public static String post(String url, Map<String, Object> headers, Map<String, Object> params) {
        try {
            HttpPost httpPost = new HttpPost(url);
            requestHeader(headers, httpPost);
            httpPost.setEntity(new UrlEncodedFormEntity(params2NVPs(params), "UTF-8"));
            return httpResult(httpPost);
        } catch (UnsupportedEncodingException e) {
            logger.error("携带头信息，参数的post请求   请求出错：" + e.getMessage(), e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json形式的post请求，参数编码UTF-8
     *
     * @param url        请求url
     * @param jsonParams 请求参数json串
     * @return
     */
    public static String post(String url, String jsonParams) {
        HttpPost httpPost = new HttpPost(url);
        StringEntity stringEntity = new StringEntity(
                jsonParams, ContentType.create("application/json", "UTF-8"));
        httpPost.setEntity(stringEntity);
        return httpResult(httpPost);
    }

    /**
     * xml形式的post请求，参数编码UTF-8
     *
     * @param url       请求url
     * @param xmlParams 请求参数xml串
     * @return
     */
    public static String postXml(String url, String xmlParams) {
        HttpPost httpPost = new HttpPost(url);
        StringEntity stringEntity = new StringEntity(
                xmlParams, ContentType.create("application/xml", "UTF-8"));
        httpPost.setEntity(stringEntity);
        return httpResult(httpPost);
    }

    /**
     * 上传
     *
     * @param url  请求url
     * @param file 上传的文件
     * @return
     */
    public static String upload(String url, File file) {
        HttpPost httpPost = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addBinaryBody("file", file, ContentType.DEFAULT_BINARY, file.getName());
        builder.setCharset(Charset.forName("UTF-8"));
        HttpEntity entity = builder.build();
        httpPost.setEntity(entity);
        return httpResult(httpPost);
    }

    /**
     * 上传
     *
     * @param url   请求url
     * @param files 上传文件的list
     * @return
     */
    public static String upload(String url, List<File> files) {
        if (files == null || files.size() == 0) {
            return null;
        }
        HttpPost httpPost = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        for (int i = 0; i < files.size(); i++) {
            builder.addBinaryBody("file_" + i, files.get(i), ContentType.DEFAULT_BINARY, files.get(i).getName());
            builder.setCharset(Charset.forName("UTF-8"));
        }
        HttpEntity entity = builder.build();
        httpPost.setEntity(entity);
        return httpResult(httpPost);
    }

    /**
     * 上传
     *
     * @param url     请求url，必须
     * @param headers 请求头，非必须，不设置传null
     * @param params  参数，非必须，不设置传null
     * @param file    上传的文件， 必须
     * @return
     */
    public static String upload(String url, Map<String, Object> headers, Map<String, Object> params, File file) {
        HttpPost httpPost = new HttpPost(url);
        requestHeader(headers, httpPost);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody("file", file, ContentType.DEFAULT_BINARY, file.getName());
        builder.setCharset(Charset.forName("UTF-8"));
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        if (params != null && params.size() > 0) {
            for (Entry<String, Object> entry : params.entrySet()) {
                builder.addTextBody(entry.getKey(), String.valueOf(entry.getValue()), ContentType.DEFAULT_BINARY);
                builder.setCharset(Charset.forName("UTF-8"));
            }
        }
        HttpEntity entity = builder.build();
        httpPost.setEntity(entity);
        return httpResult(httpPost);
    }

    /**
     * 微信多媒体上传
     *
     * @param url  请求url
     * @param file 待上传文件
     * @return
     */
    public static String uploadForWechat(String url, File file) {
        if (file == null || file.length() == 0) {
            return null;
        }
        return httpResult(wechatUploadPost(url, file, null));
    }

    /**
     * 微信永久素材中视频上传
     *
     * @param url        请求url
     * @param file       待上传文件
     * @param jsonParams json串
     * @return
     */
    public static String uploadForWechatMaterialVideo(String url, File file, String jsonParams) {
        if (file == null || file.length() == 0) {
            return null;
        }
        return httpResult(wechatUploadPost(url, file, jsonParams));
    }

    /**
     * 构建微信post请求
     *
     * @param url  请求url
     * @param file 上传的文件
     * @return HttpPost
     */
    private static HttpPost wechatUploadPost(String url, File file, String jsonParams) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Connection", "Keep-Alive");
        httpPost.setHeader("Cache-Control", "no-cache");
        httpPost.setHeader("Content-Type", "multipart/form-data;charset=UTF-8");
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addBinaryBody("media", file, ContentType.DEFAULT_BINARY, file.getName());
        if (jsonParams != null && !"".equals(jsonParams)) {
            builder.addTextBody("description", jsonParams);
        }
        builder.setCharset(Charset.forName("UTF-8"));
        HttpEntity entity = builder.build();
        httpPost.setEntity(entity);
        return httpPost;
    }

    /**
     * 请求结果处理
     *
     * @param request request
     * @return
     */
    private static String httpResult(HttpRequestBase request) {
        CloseableHttpClient httpClient = initHttpClient();
        CloseableHttpResponse response = null;
        configHttpRequest(request);
        try {
            response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 获取HttpClient
     *
     * @return
     */
    private static CloseableHttpClient initHttpClient() {
        if (httpClient != null) {
            return httpClient;
        }
        poolingHttpClientConnectionManager = createPoolingHttpClientConnectionManager();
        httpClient = createHttpClient();
        return httpClient;
    }

    /**
     * 创建PoolingHttpClientConnectionManager
     */
    private static PoolingHttpClientConnectionManager createPoolingHttpClientConnectionManager() {
        ConnectionSocketFactory csFactory = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory lcsFactory = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", csFactory).register("https", lcsFactory).build();
        PoolingHttpClientConnectionManager phccManager = new PoolingHttpClientConnectionManager(registry);
        SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).setSoReuseAddress(true).setSoTimeout(500)
                .setSoLinger(60).setSoKeepAlive(true).build();
        phccManager.setDefaultSocketConfig(socketConfig);
        phccManager.setSocketConfig(new HttpHost("somehost", 80), socketConfig);
        phccManager.setMaxTotal(MAX_TOTAL);
        phccManager.setDefaultMaxPerRoute(DEFAULT_MAX_PRR_ROUTE);
        return phccManager;
    }

    /**
     * 创建HttpClient
     *
     * @return
     */
    private static CloseableHttpClient createHttpClient() {
        HttpRequestRetryHandler httpRequestRetryHandler = (exception, executionCount, context) -> {
            if (executionCount >= EXECUTION_NUM) {
                return false;
            }
            // 连接丢失，重试
            if (exception instanceof NoHttpResponseException) {
                return true;
            }
            // SSL握手异常，不重试
            if (exception instanceof SSLHandshakeException) {
                return false;
            }
            // 超时，不重试
            if (exception instanceof InterruptedIOException) {
                return false;
            }
            // 目标服务器不可达，不重试
            if (exception instanceof UnknownHostException) {
                return false;
            }
            // SSL异常，不重试
            if (exception instanceof SSLException) {
                return false;
            }
            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            // 请求幂等，再次尝试
            return !(request instanceof HttpEntityEnclosingRequest);
        };
        return HttpClients.custom().setConnectionManager(poolingHttpClientConnectionManager)
                .setRetryHandler(httpRequestRetryHandler).setDefaultRequestConfig(requestConfig).build();
    }

    /**
     * 配置请求超时时间
     */
    private static void configHttpRequest(HttpRequestBase httpRequestBase) {
        httpRequestBase.setConfig(requestConfig);
    }

    /**
     * 参数转换
     *
     * @param params 待转换的参数
     * @return
     */
    private static List<NameValuePair> params2NVPs(Map<String, Object> params) {
        List<NameValuePair> nvpList = new ArrayList<>();
        if (params != null && params.size() > 0) {
            for (Entry<String, Object> param : params.entrySet()) {
                nvpList.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
            }
        }
        return nvpList;
    }

    /**
     * 设置请求头
     *
     * @param headers     头参数
     * @param requestBase 请求
     */
    private static void requestHeader(Map<String, Object> headers, HttpRequestBase requestBase) {
        if (headers != null && headers.size() > 0) {
            for (Entry<String, Object> entry : headers.entrySet()) {
                requestBase.setHeader(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
    }

    public static class IdleConnectionMonitorThread extends Thread {

        private final PoolingHttpClientConnectionManager connMgr;

        private volatile boolean shutdown = false;

        IdleConnectionMonitorThread(PoolingHttpClientConnectionManager connMgr) {
            super();
            this.connMgr = connMgr;
        }

        @Override
        public void run() {
            try {
                while (!shutdown) {
                    synchronized (this) {
                        wait(5000);
                        connMgr.closeExpiredConnections();
                        connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
                    }
                }
            } catch (InterruptedException ex) {
                // terminate
            }
        }
    }
}