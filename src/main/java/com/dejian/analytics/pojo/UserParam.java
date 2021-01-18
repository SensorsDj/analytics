package com.dejian.analytics.pojo;

import com.dejian.analytics.constant.column.UserColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

/**
 * 用户表参数
 *
 * @author zw
 * @date 2020/12/30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserParam extends Param {
    /**
     * 用户id
     */
    private String distinctId;
    /**
     * 是否登录
     */
    private boolean isLoginId;

    public UserParam() {
    }

    public UserParam(String distinctId) {
        this.distinctId = distinctId;
        this.isLoginId = true;
    }

    public UserParam(String distinctId, boolean isLoginId) {
        this.distinctId = distinctId;
        this.isLoginId = isLoginId;
    }

    /**
     * 设置普通参数  任一入参为空则不作操作
     *
     * @param userColumn 普通参数枚举
     * @param value      值
     */
    public UserParam addParam(UserColumn userColumn, String value) {
        if (userColumn != null && StringUtils.isNotBlank(value)) {
            this.getParams().add(new SingleParam(userColumn, value));
        }
        return this;
    }
}
