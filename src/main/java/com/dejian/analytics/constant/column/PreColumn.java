package com.dejian.analytics.constant.column;

import com.dejian.analytics.constant.TypeEnum;

import static com.dejian.analytics.constant.TypeEnum.STRING;

/**
 * 预置属性字段
 *
 * @author zw
 * @date 2020/12/29
 */
public enum PreColumn {
    //
    TIME("$time", TypeEnum.TIME),
    RECEIVE_TIME("$receive_time", TypeEnum.TIME),
    LIB("$lib", STRING),
    LIB_VERSION("$lib_version", STRING),
    ;

    PreColumn(String name, TypeEnum type) {
        this.name = name;
        this.type = type;
    }

    private final String name;
    private final TypeEnum type;

    public String getName() {
        return name;
    }

    public TypeEnum getType() {
        return type;
    }
}
