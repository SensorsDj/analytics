package com.dejian.analytics.pojo;

import com.dejian.analytics.constant.TypeEnum;
import com.dejian.analytics.constant.column.*;
import lombok.Data;

/**
 * 属性
 *
 * @author zw
 * @date 2020/12/29
 */
@Data
public class SingleParam {
    /**
     * 属性名称
     */
    private String name;
    /**
     * 属性值(如果是时间 使用时间戳)
     */
    private String value;
    /**
     * 属性类型 {@link TypeEnum}
     */
    private TypeEnum type;
    /**
     * 是否首次设置
     */
    private boolean isOnce;
    /**
     * 是否累加
     */
    private boolean isIncrement;

    public SingleParam() {
    }

    public SingleParam(ItemColumn column, String value) {
        this.name = column.getName();
        this.type = column.getType();
        this.value = value;
    }

    public SingleParam(UserColumn column, String value) {
        this.name = column.getName();
        this.value = value;
        this.type = column.getType();
        this.isOnce = column.isOnce();
        this.isIncrement = column.isIncrement();
    }

    public SingleParam(EventColumn column, String value) {
        this.name = column.getName();
        this.value = value;
        this.type = column.getType();
    }

    public SingleParam(PreColumn column, String value) {
        this.name = column.getName();
        this.type = column.getType();
        this.value = value;
    }

    public SingleParam(SuperColumn column, String value) {
        this.name = column.getName();
        this.type = column.getType();
        this.value = value;
    }

    public SingleParam(String name, String value, TypeEnum type, boolean isOnce, boolean isIncrement) {
        this.name = name;
        this.value = value;
        this.type = type;
        this.isOnce = isOnce;
        this.isIncrement = isIncrement;
    }
}
