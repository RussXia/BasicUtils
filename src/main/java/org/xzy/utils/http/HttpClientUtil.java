package org.xzy.utils.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;

/* 
 * 利用HttpClient进行post请求的工具类 
 */
@Slf4j
public class HttpClientUtil {


    private static ConnectionKeepAliveStrategy collectionStrategy = new ConnectionKeepAliveStrategy() {
        public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
            // 解析http响应中的Keep-Alive头消息
            HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
            while (it.hasNext()) {
                HeaderElement header = it.nextElement();
                String param = header.getName();
                String value = header.getValue();
                // 如果制定了超时时间，则连接的存活时间为timeout的值
                if (value != null && param.equalsIgnoreCase("timeout")) {
                    try {
                        return Long.parseLong(value) * 1000;
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("Value format error.", e);
                    }
                }
            }
            // 否则默认存活30秒
            return 30 * 1000;
        }
    };

    private static PoolingHttpClientConnectionManager collectionManager = new PoolingHttpClientConnectionManager();

    static {
        // 连接池最大连接并发数
        collectionManager.setMaxTotal(2);
        // 单路由最大连接并发数
        collectionManager.setDefaultMaxPerRoute(20);
    }

    public static HttpResponse doGet(String url) {
        CloseableHttpClient httpClient = HttpClients.custom().setKeepAliveStrategy(collectionStrategy)
                .setConnectionManager(collectionManager).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded");
        HttpContext context = HttpClientContext.create();
        try {
            return httpClient.execute(httpGet, context);
        } catch (IOException e) {
            throw new RuntimeException("Get error.", e);
        }
    }

    public static JSONObject HttpsGet(String url) {

        HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

        DefaultHttpClient client = new DefaultHttpClient();

        SchemeRegistry registry = new SchemeRegistry();
        SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
        socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
        registry.register(new Scheme("https", socketFactory, 443));
        SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);
        DefaultHttpClient httpClient = new DefaultHttpClient(mgr, client.getParams());

        HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);


        HttpGet httpGet = new HttpGet(url);
        JSONObject result = new JSONObject();
        CloseableHttpResponse response = null;
        RequestConfig requestConfig = RequestConfig.custom().
                setSocketTimeout(2000).setConnectTimeout(2000).build();
        httpGet.setConfig(requestConfig);

        try {
            response = httpClient.execute(httpGet, new BasicHttpContext());

            if (response.getStatusLine().getStatusCode() != 200) {

                log.info("request url failed, http code=" + response.getStatusLine().getStatusCode()
                        + ", url=" + url);
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity, "utf-8");
                result = JSON.parseObject(resultStr);
                if (result.getInteger("errcode") == 0) {
                    return result;
                } else {
                    log.info("request url=" + url + ",return value=" + resultStr);
                    int errCode = result.getInteger("errcode");
                    String errMsg = result.getString("errmsg");
                }
            }
        } catch (IOException e) {
            log.error("request url=" + url + ", exception, msg=" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (response != null) try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static JSONObject HttpsPost(String url, Object data) {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
        DefaultHttpClient client = new DefaultHttpClient();
        SchemeRegistry registry = new SchemeRegistry();
        SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
        socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
        registry.register(new Scheme("https", socketFactory, 443));
        SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);
        DefaultHttpClient httpClient = new DefaultHttpClient(mgr, client.getParams());
        HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
        RequestConfig requestConfig = RequestConfig.custom().
                setSocketTimeout(2000).setConnectTimeout(2000).build();
        httpPost.setConfig(requestConfig);
        httpPost.addHeader("Content-Type", "application/json");

        try {
            StringEntity requestEntity = new StringEntity(JSON.toJSONString(data), "utf-8");

            httpPost.setEntity(requestEntity);

            response = httpClient.execute(httpPost, new BasicHttpContext());

            if (response.getStatusLine().getStatusCode() != 200) {

                log.info("request url failed, http code=" + response.getStatusLine().getStatusCode()
                        + ", url=" + url);
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity, "utf-8");

                JSONObject result = JSON.parseObject(resultStr);
                return result;
            }
        } catch (IOException e) {
            log.error("request url=" + url + ", exception, msg=" + e.getMessage());
        } finally {
            if (response != null) try {
                response.close();
            } catch (IOException e) {
                log.error("Closing CloseableHttpResponse Failed!",e);
                e.printStackTrace();
            }
        }
        return null;
    }
}