package com.dejian.analytics.constant.column;

import com.dejian.analytics.constant.TypeEnum;

import static com.dejian.analytics.constant.TypeEnum.*;

/**
 * items表字段
 *
 * @author zw
 * @date 2020/12/29
 */
public enum ItemColumn {
    /**
     * 内容类型
     * 1 电子书
     * 2 对话小说
     * 3 漫画
     * 4 听书阿斯蒂
     */
    CONTENT_TYPE("content_type", STRING, false, false),
    /**
     * 小说名称
     */
    BOOK_NAME("book_name", STRING, false, false),
    /**
     * 小说文档类型
     * 1 在线小说
     * 2 本地小说
     */
    DOCUMENT_TYPE("document_type", STRING, false, false),
    /**
     * 小说状态
     * 1 完结
     * 2 连载
     */
    COMPLETE_STATE("complete_state", STRING, false, false),
    /**
     * 上架时间 首次上架时间
     */
    PUT_AWAY_DATE("put_away_date", DATETIME, true, false),
    /**
     * 是否上架
     */
    PUT_AWAY_STATE("put_away_state", BOOLEAN, false, false),
    /**
     * 关键字
     */
    KEY_WORD("key_word", LIST, false, false),
    /**
     * 标签
     */
    TAG("tag", LIST, false, false),
    /**
     * 书籍简介
     */
    LONG_DESC("long_desc", STRING, false, false),
    /**
     * 长推荐
     */
    RECOMMEND_LAN("recommend_lan", STRING, false, false),
    /**
     * 图书展示名
     */
    DISPLAY_BOOK_NAME("display_book_name", STRING, false, false),
    /**
     * 总字数
     */
    WORD_COUNT("word_count", LONG, false, false),
    /**
     * 总章节数
     */
    CHAPTER_COUNT("chapter_count", LONG, false, false),
    /**
     * 小说评分 取数据计算后的真实评分，非运营评分
     */
    BOOK_RATING("book_rating", LONG, false, false),
    /**
     * 一级展示分类
     */
    CHANNEL_CAT_PATHS("channel_cat_paths", STRING, false, false),
    /**
     * 二级展示分类
     */
    SECONDARY_CLASSIFICATION("secondary_classification", STRING, false, false),
    /**
     * 三级展示分类
     */
    THREE_LEVEL_CLASSIFICATION("three_level_classification", STRING, false, false),
    /**
     * 四级展示分类
     */
    FOUR_LEVEL_CLASSIFICATION("four_level_classification", STRING, false, false),
    /**
     * 五级展示分类
     */
    FIVE_LEVEL_CLASSIFICATION("five_level_classification", STRING, false, false),
    /**
     * 小说付费类型
     */
    SELL_STATUS("sell_status", STRING, false, false),
    /**
     * 更新章节时间
     */
    LAST_CHAPTER_TIME("last_chapter_time", DATETIME, false, false),
    /**
     * 优质等级 A,B,C,D
     */
    LEVEL_CODE("level_code", STRING, false, false),
    /**
     * 是否付费 书籍属性，只要任意章节有付费即报付费
     */
    IS_PAY("is_pay", BOOLEAN, false, false),
    /**
     * 版权方
     */
    FROM_SOURCE("from_source", STRING, false, false),
    /**
     * 作者ID
     */
    AUTHOR_ID("author_id", STRING, false, false),
    /**
     * 作者笔名
     */
    BOOK_AUTHOR("book_author", STRING, false, false),
    /**
     * 作者等级
     */
    AUTHOR_RANK("author_rank", STRING, false, false),
    ;

    ItemColumn(String name, TypeEnum type, boolean isOnce, boolean isIncrement) {
        this.name = name;
        this.type = type;
        this.isOnce = isOnce;
        this.isIncrement = isIncrement;
    }

    private final String name;
    private final TypeEnum type;
    private final boolean isOnce;
    private final boolean isIncrement;

    public String getName() {
        return name;
    }

    public TypeEnum getType() {
        return type;
    }

    public boolean isOnce() {
        return isOnce;
    }

    public boolean isIncrement() {
        return isIncrement;
    }
}
