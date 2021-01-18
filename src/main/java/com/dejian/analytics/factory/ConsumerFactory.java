package com.dejian.analytics.factory;

import com.dejian.analytics.consume.DebugConsumer;
import com.dejian.analytics.pojo.conumer.SensorsConsumer;
import com.sensorsdata.analytics.javasdk.SensorsAnalytics;
import org.apache.http.HttpHost;

import java.io.IOException;

/**
 * 打点工厂
 *
 * @author zw
 * @date 2020/12/29
 */
public class ConsumerFactory {
    private static SensorsAnalytics sa = null;

    /**
     * 离线工厂,配合logAgent使用
     *
     * @param logUrl 日志位置
     * @return SensorsAnalytics
     */
    public static SensorsConsumer asyncConsumer(String logUrl, String lockFileName) throws Exception {
        try {
            SensorsAnalytics sensorsAnalytics = new SensorsAnalytics(new SensorsAnalytics.ConcurrentLoggingConsumer(logUrl, lockFileName));
            return new SensorsConsumer(true, sensorsAnalytics);
        } catch (IOException e) {
            throw new Exception("create consumer error. logUrl=" + logUrl);
        }
    }

    /**
     * 实时工厂 - 工厂
     * DebugConsumer
     *
     * @param logUrl 接受数据url
     * @return SensorsAnalytics
     */
    public static SensorsConsumer syncConsumer(String logUrl, HttpHost proxy) {
        return new SensorsConsumer(new SensorsAnalytics(new DebugConsumer(logUrl, true, proxy)));
    }

    /**
     * 实时工厂 - 单例
     * DebugConsumer
     *
     * @param logUrl 接受数据url
     * @return SensorsAnalytics
     */
    public static SensorsConsumer syncSingleConsumer(String logUrl, HttpHost proxy) {
        if (sa == null) {
            sa = new SensorsAnalytics(new DebugConsumer(logUrl, true, proxy));
        }
        return new SensorsConsumer(sa);
    }
}
