package com.dejian.analytics.pojo;

import com.dejian.analytics.constant.column.EventColumn;
import com.dejian.analytics.constant.column.PreColumn;
import com.dejian.analytics.constant.column.SuperColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 事件属性类
 *
 * @author zw
 * @date 2020/12/30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EventParam extends Param{
    /**
     * 用户ID
     */
    private String distinctId;

    /**
     * 是否登录
     */
    private boolean isLogin;

    /**
     * 事件名称
     */
    private String eventName;

    /**
     * 预置参数，插件自带，可进行覆盖
     */
    private List<SingleParam> preParams = new ArrayList<>();

    /**
     * 公共参数
     */
    private List<SingleParam> superParams = new ArrayList<>();

    public EventParam() {
    }

    public EventParam(String distinctId, String eventName) {
        this.distinctId = distinctId;
        this.isLogin = true;
        this.eventName = eventName;
    }

    public EventParam(String distinctId, boolean isLogin, String eventName) {
        this.distinctId = distinctId;
        this.isLogin = isLogin;
        this.eventName = eventName;
    }

    /**
     * 设置普通参数  任一入参为空则不作操作
     *
     * @param eventColumn 普通参数枚举
     * @param value       值
     */
    public EventParam addParam(EventColumn eventColumn, String value) {
        if (eventColumn != null && StringUtils.isNotBlank(value)) {
            this.getParams().add(new SingleParam(eventColumn, value));
        }
        return this;
    }

    /**
     * 设置普通参数  任一入参为空则不作操作
     *
     * @param preColumn 普通参数枚举
     * @param value     值
     */
    public EventParam addPreParam(PreColumn preColumn, String value) {
        if (preColumn != null && StringUtils.isNotBlank(value)) {
            this.getParams().add(new SingleParam(preColumn, value));
        }
        return this;
    }

    /**
     * 设置普通参数  任一入参为空则不作操作
     *
     * @param superColumn 普通参数枚举
     * @param value       值
     */
    public EventParam addSuperParam(SuperColumn superColumn, String value) {
        if (superColumn != null && StringUtils.isNotBlank(value)) {
            this.getParams().add(new SingleParam(superColumn, value));
        }
        return this;
    }
}
