package com.dejian.analytics.constant.column;

import com.dejian.analytics.constant.TypeEnum;

import static com.dejian.analytics.constant.TypeEnum.*;

/**
 * items表字段
 *
 * @author zw
 * @date 2020/12/29
 */
public enum UserColumn {
    /**
     * 昵称
     */
    NICK_NAME("nick_name", STRING, false, false),
    /**
     * 首次注册账号时间
     */
    SIGNUP_TIME("$signup_time", DATETIME, true, false),
    /**
     * 账号状态
     */
    ACCOUNT_STATE("account_state", STRING, false, false),
    /**
     * 是否VIP
     */
    IS_VIP("is_vip", BOOLEAN, false, false),
    /**
     * 购买VIP时是否过期状态
     */
    BUY_TYPE("buy_type", STRING, false, false),
    /**
     * VIP到期时间
     */
    DUE_DATE("due_date", DATETIME, false, false),
    /**
     * 首次购买VIP时间
     */
    FIRST_BUY_TIME("first_buy_time", DATETIME, true, false),
    /**
     * 最近一次购买VIP时间
     */
    LAST_BUY_TIME("last_buy_time", DATETIME, false, false),
    /**
     * 首次购买VIP类型
     */
    FIRST_BUY_TYPE("first_buy_type", STRING, true, false),
    /**
     * 最近一次购买VIP类型
     */
    LAST_BUY_TYPE("last_buy_type", STRING, false, false),
    /**
     * APP内是否开启签到提醒
     */
    IS_REMIND_SIGN_IN("is_remind_sign_in", BOOLEAN, false, false),
    /**
     * 连续签到天数（补签也算）
     */
    SIGN_IN_DAYS("sign_in_days", LONG, false, false),
    /**
     * 是否绑定手机号
     */
    IS_BIND_PHONE_NUMBER("is_bind_phone_number", BOOLEAN, false, false),
    /**
     * 全屏翻页
     */
    IS_FULLSCREEN_TURNING("is_fullscreen_turning", BOOLEAN, false, false),
    /**
     * 进度显示范围
     */
    PROGRESS_DISPLAY_RANGE("progress_display_range", STRING, false, false),
    /**
     * 页码显示形式
     */
    PAGE_DISPLAY_FORM("page_display_form", STRING, false, false),
    /**
     * 休息提醒
     */
    REST_REMINDER("rest_reminder", STRING, false, false),
    /**
     * 自动锁屏时间
     */
    CLOSING_TIME("closing_time", STRING, false, false),
    /**
     * 自动订阅
     */
    AUTO_SUBSCRIBE("auto_subscribe", LIST, false, false),
    /**
     * 是否开启了同步阅读进度的设置项
     */
    IS_SYNCHRONIZED_PROGRESS("is_synchronized_progress", BOOLEAN, false, false),
    /**
     * 阅读偏好
     */
    READING_PREFERENCE("reading_preference", LIST, false, false),
    /**
     * 夜间模式
     */
    IS_NIGHT_MODE("is_night_mode", BOOLEAN, false, false),
    /**
     * 当前拥有金币数
     */
    GOLD_NUMBER("gold_number", LONG, false, false),
    /**
     * 当前充值墨宝数
     */
    RECHARGE_MOBAO_NUMBER("recharge_mobao_number", LONG, false, false),
    /**
     * 当前赠送墨宝数
     */
    PRESENT_MOBAO_NUMBER("present_mobao_number", LONG, false, false),
    /**
     * 累计总阅读时长（全体的，包含tts、听书、对话小说）
     */
    CUMULATIVE_SPENDING_TIME("cumulative_spending_time", LONG, false, false),
    /**
     * 累积听书时长
     */
    CUMULATIVE_LISTENING_TIME("cumulative_listening_time", LONG, false, false),
    /**
     * 累积tts时长
     */
    CUMULATIVE_TTS_TIME("cumulative_tts_time", LONG, false, false),
    /**
     * 累积看书时长（文字书，漫画）
     */
    CUMULATIVE_READING_TIME("cumulative_reading_time", LONG, false, false),
    /**
     * 累积对话小说阅读时长
     */
    CUMULATIVE_DIALOG_READING_TIME("cumulative_dialog_reading_time", LONG, false, false),
    /**
     * 首次安装渠道来源
     */
    UTM_SOURCE("$utm_source", STRING, true, false),
    /**
     * 是否系统开启通知
     */
    IS_OPEN_NOTIFICATION("is_open_notification", BOOLEAN, false, false),
    /**
     * 消耗充值墨宝数
     */
    CONSUME_RECHARGE_MOBAO("consume_recharge_mobao", LONG, false, false),
    /**
     * 消耗赠送墨宝数
     */
    CONSUME_PRESENT_MOBAO("consume_present_mobao", LONG, false, false),
    /**
     * App内的是否横屏阅读
     */
    IS_HORIZONTAL_SCREEN("is_horizontal_screen", BOOLEAN, false, false),
    /**
     * 首次登录时间
     */
    FIRST_LOGIN_TIME("first_login_time", DATETIME, true, false),
    /**
     * 渠道号
     */
    CHANNEL_ID("channel_id", STRING, false, false),
    /**
     * 内部版本号
     */
    VERSION_ID("version_id", STRING, false, false),
    /**
     * 平台
     */
    PLATFORM("platform", STRING, false, false),
    /**
     * 翻页方式
     */
    TURNING_PAGE("turning_page", STRING, false, false),
    /**
     * 机型
     */
    PHONE_TYPE("phone_type", STRING, false, false),
    /**
     * 系统版本
     */
    SYSTEM_VERSION("system_version", STRING, false, false),
    ;

    UserColumn(String name, TypeEnum type, boolean isOnce, boolean isIncrement) {
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
