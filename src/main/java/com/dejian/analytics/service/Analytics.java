package com.dejian.analytics.service;

import com.dejian.analytics.constant.column.EventColumn;
import com.dejian.analytics.constant.column.SuperColumn;
import com.dejian.analytics.factory.ConsumerFactory;
import com.dejian.analytics.pojo.EventParam;
import com.dejian.analytics.pojo.ItemParam;
import com.dejian.analytics.pojo.SingleParam;
import com.dejian.analytics.pojo.UserParam;
import com.dejian.analytics.pojo.conumer.SensorsConsumer;
import com.dejian.analytics.util.ParamUtil;
import com.sensorsdata.analytics.javasdk.SensorsAnalytics;
import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpHost;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据分析方法
 *
 * @author zw
 * @date 2020/12/24
 */
@Slf4j
@Data
public class Analytics {
    private final SensorsAnalytics sa;

    private String fileName;
    private String filePath;
    private boolean isLogAgent;

    public Analytics(String url) {
        sa = buildConsumer(url, null);
    }

    /**
     * 维度表数据入库
     */
    public void itemSet(ItemParam itemParam) {
        try {
            // 校验打点文件
            if (this.isLogAgent) {
                checkLogPath();
            }
            sa.itemSet(itemParam.getItemType(), itemParam.getItemId(), ParamUtil.paramToMap(itemParam.getParams()));
            sa.flush();
        } catch (Exception e) {
            log.error("itemSet error.", e);
        }
    }

    public Analytics(String url, HttpHost proxy) {
        sa = buildConsumer(url, proxy);
    }

    /**
     * 维度表数据删除
     */
    public void itemDelete(ItemParam itemParam) {
        try {
            sa.itemDelete(itemParam.getItemType(), itemParam.getItemId(), null);
            sa.flush();
        } catch (Exception e) {
            log.error("itemDelete error.", e);
        }
    }

    /**
     * 用户表数据入库
     */
    public void userSet(UserParam userParam) {
        if (CollectionUtils.isEmpty(userParam.getParams())) {
            return;
        }
        // 校验打点文件
        if (this.isLogAgent) {
            checkLogPath();
        }
        // 打点
        userSetNormal(userParam);
        userSetOnce(userParam);
        userSetIncrement(userParam);
    }

    /**
     * 普通更新字段入库
     */
    private void userSetNormal(UserParam userParam) {
        try {
            List<SingleParam> normalParams = userParam.getParams().stream()
                    .filter(p -> !p.isIncrement())
                    .filter(p -> !p.isOnce())
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(normalParams)) {
                return;
            }
            sa.profileSet(userParam.getDistinctId(), userParam.isLoginId(), ParamUtil.paramToMap(normalParams));
            sa.flush();
        } catch (Exception e) {
            log.error("userSetNormal error.", e);
        }
    }

    /**
     * 首次设置字段入库
     */
    private void userSetOnce(UserParam userParam) {
        try {
            // 首次设置数据
            List<SingleParam> onceParams = userParam.getParams().stream().filter(SingleParam::isOnce).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(onceParams)) {
                return;
            }
            sa.profileSetOnce(userParam.getDistinctId(), userParam.isLoginId(), ParamUtil.paramToMap(onceParams));
            sa.flush();
        } catch (Exception e) {
            log.error("userSetOnce error.", e);
        }
    }

    /**
     * 累计字段入库
     */
    private void userSetIncrement(UserParam userParam) {
        try {
            List<SingleParam> incrementParams = userParam.getParams().stream().filter(SingleParam::isIncrement).collect(
                    Collectors.toList());
            if (CollectionUtils.isEmpty(incrementParams)) {
                return;
            }
            sa.profileIncrement(userParam.getDistinctId(), userParam.isLoginId(), ParamUtil.paramToMap(incrementParams));
            sa.flush();
        } catch (Exception e) {
            log.error("userSetIncrement error.", e);
        }
    }

    /**
     * 事件打点
     */
    public void eventSet(EventParam eventParam) {
        try {
            // 校验打点文件
            if (this.isLogAgent) {
                checkLogPath();
            }
            // 设置公共属性
            sa.registerSuperProperties(ParamUtil.paramToMap(eventParam.getSuperParams()));
            List<SingleParam> params = eventParam.getParams();
            params.addAll(eventParam.getPreParams());
            // 设置时间追踪
            sa.track(eventParam.getDistinctId(), eventParam.isLogin(), eventParam.getEventName(), ParamUtil.paramToMap(params));
            sa.flush();
        } catch (Exception e) {
            log.error("eventSet error.", e);
        }
    }

    /**
     * 关闭方法
     */
    public void shutdown() {
        if (sa != null) {
            sa.shutdown();
        }
    }

    /**
     * 正式consumer
     *
     * @param url 日志位置
     */
    private SensorsAnalytics buildConsumer(String url, HttpHost proxy) {
        try {
            // 获取文件信息
            if (!url.startsWith("http")) {
                this.filePath = url.substring(0, url.lastIndexOf("/") + 1);
                this.fileName = url.substring(url.lastIndexOf("/") + 1);
            }
            String fileSuffix = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
            // 创建consumer
            SensorsConsumer sensorsConsumer = ConsumerFactory.syncSingleConsumer(url, proxy);
            if (sensorsConsumer.isLogAgent()) {
                this.isLogAgent = true;
                checkLogPath();
            }
            return sensorsConsumer.getSensorsAnalytics();
        } catch (Exception e) {
            log.error("buildConsumer error.", e);
        }
        return null;
    }

    public static void main(String[] args) throws InterruptedException {
        String filePath = "D:/data/sensors/";
        String fileName = "sensors";

        HttpHost proxy = new HttpHost("192.168.7.24", 9998);
        for (int i = 0; i < 1000000; i++) {
            Analytics as = new Analytics(filePath + fileName, null);
            EventParam eventParam = new EventParam("123", "test");
            eventParam.addParam(EventColumn.ITEM_TYPE, "book").addSuperParam(SuperColumn.CHANNEL_ID, "124002");
            as.eventSet(eventParam);
        }
    }

    /**
     * 当使用ConcurrentLoggingConsumer时，校验日志文件是否存在，若不存在则创建
     */
    private void checkLogPath() {
        if (!this.isLogAgent) return;
        // 判断日志文件是否存在
        String fileSuffix = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        File path = new File(this.filePath);
        File file = new File(this.filePath + fileName + "." + fileSuffix);
        if (!file.exists()) {
            try {
                path.mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                log.error("checkLogPath error.", e);
            }
        }
    }
}
