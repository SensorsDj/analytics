package com.dejian.analytics.constant.column;

import com.dejian.analytics.constant.TypeEnum;

import static com.dejian.analytics.constant.TypeEnum.*;

/**
 * 事件打点字段
 *
 * @author zw
 * @date 2020/12/29
 */
public enum EventColumn {
    /**
     * 是否成功
     */
    IS_SUCCESS("is_success", BOOLEAN),
    /**
     * 登录方式
     */
    LOGIN_METHOD("login_method", STRING),
    /**
     * 失败原因
     */
    FAIL_REASON("fail_reason", STRING),
    /**
     * 登录来源
     */
    LOGIN_SOURCE("login_source", STRING),
    /**
     * 点赞类型
     */
    LIKE_TYPE("like_type", STRING),
    /**
     * 小说ID
     */
    ITEM_ID("item_id", STRING),
    /**
     * 内容类型
     */
    ITEM_TYPE("item_type", STRING),
    /**
     * 章节序号
     */
    CHAPTERS_ID("chapters_id", LONG),
    /**
     * 句子ID
     */
    SENTENCE_ID("sentence_id", STRING),
    /**
     * 是否首次
     */
    IS_FIRST_TIME("$is_first_time", BOOLEAN),
    /**
     * 评论ID
     */
    COMMENT_ID("comment_id", STRING),
    /**
     * 评论类型
     */
    COMMENT_TYPE("comment_type", STRING),
    /**
     * 评分
     */
    BOOK_SCORE("book_score", LONG),
    /**
     * 评论内容
     */
    COMMENTS("comments", STRING),
    ;

    EventColumn(String name, TypeEnum type) {
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
