package com.dejian.analytics.util;

import com.dejian.analytics.pojo.SingleParam;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 参数工具
 *
 * @author zw
 * @date 2020/12/29
 */
public class ParamUtil {
    public static Map<String, Object> paramToMap(List<SingleParam> params) {
        Map<String, Object> properties = new LinkedHashMap<>();
        for (SingleParam singleParam : params) {
            if (StringUtils.isNotBlank(singleParam.getName())
                    && StringUtils.isNotBlank(singleParam.getValue())
                    && singleParam.getType() != null) {
                properties.put(singleParam.getName(), getTypeValue(singleParam));
            }
        }
        return properties;
    }

    private static Object getTypeValue(SingleParam singleParam) {
        Gson gson = new Gson();
        switch (singleParam.getType()) {
            case TIME:
            case LONG:
                return Long.parseLong(singleParam.getValue());
            case DOUBLE:
                return Double.parseDouble(singleParam.getValue());
            case INTEGER:
                return Integer.parseInt(singleParam.getValue());
            case BOOLEAN:
                return Boolean.parseBoolean(singleParam.getValue());
            case DATETIME:
                return new Date(Long.parseLong(singleParam.getValue()));
            case LIST:
                return gson.fromJson(singleParam.getValue(), List.class);
            case STRING:
            default:
                return singleParam.getValue();
        }
    }
}
