package com.dejian.analytics.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 参数公共类
 *
 * @author zw
 * @date 2020/12/30
 */
@Data
public class Param {
    /**
     * 基本参数
     */
    private List<SingleParam> params = new ArrayList<>();
}
