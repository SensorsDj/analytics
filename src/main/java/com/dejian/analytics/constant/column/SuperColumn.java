package com.dejian.analytics.constant.column;

import com.dejian.analytics.constant.TypeEnum;

import static com.dejian.analytics.constant.TypeEnum.BOOLEAN;
import static com.dejian.analytics.constant.TypeEnum.STRING;

/**
 * items表字段
 *
 * @author zw
 * @date 2020/12/29
 */
public enum SuperColumn {
    //
    APP_NAME("app_name", STRING),
    PLATFORM_TYPE("platform_type", STRING),
    IS_LOGIN("is_login", BOOLEAN),
    IS_VIP("is_vip", BOOLEAN),
    CHANNEL_ID("channel_id", STRING),
    VERSION_NUMBER("version_number", STRING),
    ;

    SuperColumn(String name, TypeEnum type) {
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
