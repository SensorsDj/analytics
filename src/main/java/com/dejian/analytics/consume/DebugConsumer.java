package com.dejian.analytics.consume;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.sensorsdata.analytics.javasdk.SensorsAnalytics;
import com.sensorsdata.analytics.javasdk.exceptions.DebugModeException;
import com.sensorsdata.analytics.javasdk.util.Base64Coder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.GZIPOutputStream;

/**
 * 新建consume,可设置代理
 *
 * @author zw
 * @date 2020/12/29
 */
@Data
@Slf4j
public class DebugConsumer implements SensorsAnalytics.Consumer {
    final HttpConsumer httpConsumer;
    final ObjectMapper jsonMapper;

    public DebugConsumer(final String serverUrl, final boolean writeData, HttpHost proxy) {
        String debugUrl;
        try {
            // 将 URI Path 替换成 Debug 模式的 '/debug'
            URIBuilder builder = new URIBuilder(new URI(serverUrl));
            String[] urlPath = builder.getPath().split("/");
            urlPath[urlPath.length - 1] = "debug";
            builder.setPath(strJoin(urlPath, "/"));
            debugUrl = builder.build().toURL().toString();
        } catch (URISyntaxException | MalformedURLException e) {
            throw new DebugModeException(e);
        }

        Map<String, String> headers = new HashMap<>();
        if (!writeData) {
            headers.put("Dry-Run", "true");
        }
        this.httpConsumer = new HttpConsumer(debugUrl, headers, proxy);
        this.jsonMapper = getJsonObjectMapper();
    }

    @Override
    public void send(Map<String, Object> message) {
        // XXX: HttpConsumer 只处理了 Message List 的发送？
        List<Map<String, Object>> messageList = new ArrayList<>();
        messageList.add(message);

        String sendingData;
        try {
            sendingData = jsonMapper.writeValueAsString(messageList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize data.", e);
        }
        log.info("==========================================================================");
        try {
            synchronized (httpConsumer) {
                httpConsumer.consume(sendingData);
            }
            log.info(String.format("valid message: %s", sendingData));
        } catch (IOException | HttpConsumer.HttpConsumerException e) {
            throw new DebugModeException(e);
        }
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() {
        httpConsumer.close();
    }

    private static String strJoin(String[] arr, String sep) {
        StringBuilder sbStr = new StringBuilder();
        for (int i = 0, il = arr.length; i < il; i++) {
            if (i > 0) {
                sbStr.append(sep);
            }
            sbStr.append(arr[i]);
        }
        return sbStr.toString();
    }

    private static ObjectMapper getJsonObjectMapper() {
        ObjectMapper jsonObjectMapper = new ObjectMapper();
        // 容忍json中出现未知的列
        jsonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 兼容java中的驼峰的字段名命名
        jsonObjectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        jsonObjectMapper.setTimeZone(TimeZone.getDefault());
        jsonObjectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
        return jsonObjectMapper;
    }

    private static class HttpConsumer implements Closeable {
        private static final String SDK_VERSION = "3.1.16";
        CloseableHttpClient httpClient;
        final String serverUrl;
        final Map<String, String> httpHeaders;
        final boolean compressData;
        final HttpHost proxy;

        @Getter
        static class HttpConsumerException extends Exception {
            HttpConsumerException(String error, String sendingData, int httpStatusCode, String httpContent) {
                super(error);
                this.sendingData = sendingData;
                this.httpStatusCode = httpStatusCode;
                this.httpContent = httpContent;
            }

            final String sendingData;
            final int httpStatusCode;
            final String httpContent;
        }

        HttpConsumer(String serverUrl, Map<String, String> httpHeaders, HttpHost proxy) {
            this.serverUrl = serverUrl.trim();
            this.httpHeaders = httpHeaders;
            this.compressData = true;
            this.proxy = proxy;
        }

        synchronized void consume(final String data) throws IOException, HttpConsumer.HttpConsumerException {
            HttpUriRequest request = getHttpRequest(data);
            if (httpClient == null) {
                HttpClientBuilder customBuilder = HttpClients.custom();
                if (proxy != null) {
                    customBuilder.setProxy(proxy);
                }
                httpClient = customBuilder.setUserAgent("SensorsAnalytics Java SDK " + SDK_VERSION).build();
            }
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int httpStatusCode = response.getStatusLine().getStatusCode();
                if (httpStatusCode < 200 || httpStatusCode >= 300) {
                    String httpContent = new String(EntityUtils.toByteArray(response.getEntity()), StandardCharsets.UTF_8);
                    throw new HttpConsumerException(
                            String.format("Unexpected response %d from Sensors Analytics: %s", httpStatusCode, httpContent), data,
                            httpStatusCode, httpContent);
                }
            }
        }

        HttpUriRequest getHttpRequest(final String data) throws IOException {
            HttpPost httpPost = new HttpPost(this.serverUrl);
            httpPost.setEntity(getHttpEntry(data));
            if (this.httpHeaders != null) {
                for (Map.Entry<String, String> entry : this.httpHeaders.entrySet()) {
                    httpPost.addHeader(entry.getKey(), entry.getValue());
                }
            }
            return httpPost;
        }

        UrlEncodedFormEntity getHttpEntry(final String data) throws IOException {
            byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            if (compressData) {
                ByteArrayOutputStream os = new ByteArrayOutputStream(bytes.length);
                GZIPOutputStream gos = new GZIPOutputStream(os);
                gos.write(bytes);
                gos.close();
                byte[] compressed = os.toByteArray();
                os.close();

                nameValuePairs.add(new BasicNameValuePair("gzip", "1"));
                nameValuePairs.add(new BasicNameValuePair("data_list", new String(Base64Coder.encode
                        (compressed))));
            } else {
                nameValuePairs.add(new BasicNameValuePair("gzip", "0"));
                nameValuePairs.add(new BasicNameValuePair("data_list", new String(Base64Coder.encode
                        (bytes))));
            }
            return new UrlEncodedFormEntity(nameValuePairs);
        }

        @Override
        public synchronized void close() {
            try {
                if (httpClient != null) {
                    httpClient.close();
                    httpClient = null;
                }
            } catch (IOException ignored) {
            }
        }
    }
}

