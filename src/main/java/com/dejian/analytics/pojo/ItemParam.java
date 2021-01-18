package com.dejian.analytics.pojo;

import com.dejian.analytics.constant.column.ItemColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

/**
 * 维度表设置参数
 *
 * @author zw
 * @date 2020/12/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ItemParam extends Param {
    /**
     * item类型
     */
    private String itemType;
    /**
     * 小说idID
     */
    private String itemId;

    public ItemParam() {
    }

    public ItemParam(String itemType, String itemId) {
        this.itemType = itemType;
        this.itemId = itemId;
    }

    /**
     * 设置普通参数  任一入参为空则不作操作
     *
     * @param itemColumn 普通参数枚举
     * @param value      值
     */
    public ItemParam addParam(ItemColumn itemColumn, String value) {
        if (itemColumn != null && StringUtils.isNotBlank(value)) {
            this.getParams().add(new SingleParam(itemColumn, value));
        }
        return this;
    }
}
