package com.dejian.analytics.pojo.conumer;

import com.sensorsdata.analytics.javasdk.SensorsAnalytics;
import lombok.Data;

/**
 * 消费者封装
 *
 * @author zw
 * @date 2021/1/11
 */
@Data
public class SensorsConsumer {
    private boolean isLogAgent;
    private SensorsAnalytics sensorsAnalytics;

    public SensorsConsumer(SensorsAnalytics sensorsAnalytics) {
        this.sensorsAnalytics = sensorsAnalytics;
    }

    public SensorsConsumer(boolean isLogAgent, SensorsAnalytics sensorsAnalytics) {
        this.isLogAgent = isLogAgent;
        this.sensorsAnalytics = sensorsAnalytics;
    }
}
