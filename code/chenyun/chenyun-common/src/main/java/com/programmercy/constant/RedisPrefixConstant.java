package com.programmercy.constant;

/**
 * Description: redis 中键的前缀
 * Created by 爱吃小鱼的橙子 on 2024-11-14 14:57
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
public class RedisPrefixConstant {

    /**
     * 各个表对应的发号器
     */
    public static final String AUDIT_LOG_SEQUENCE = "sequence:auditLog:id";
    public static final String BLOG_COMMENT_SEQUENCE = "sequence:blogComment:id";
    public static final String BLOG_POST_SEQUENCE = "sequence:blogPost:id";
    public static final String BLOG_POST_TAG_SEQUENCE = "sequence:blogPostTag:id";
    public static final String BOOKMARK_SEQUENCE = "sequence:bookmark:id";
    public static final String FRIENDSHIP_SEQUENCE = "sequence:friendship:id";
    public static final String GROUP_SEQUENCE = "sequence:group:id";
    public static final String GROUP_CHAT_SEQUENCE = "sequence:groupChat:id";
    public static final String GROUP_MEMBER_SEQUENCE = "sequence:groupMember:id";
    public static final String GROUP_MESSAGE_SEQUENCE = "sequence:groupMessage:id";
    public static final String LOCATION_SEQUENCE = "sequence:location:id";
    public static final String MESSAGE_SEQUENCE = "sequence:message:id";
    public static final String NOTIFICATION_SEQUENCE = "sequence:notification:id";
    public static final String PRIVATE_CHAT_SEQUENCE = "sequence:privateChat:id";
    public static final String REPORT_SEQUENCE = "sequence:report:id";
    public static final String REVIEW_QUEUE_SEQUENCE = "sequence:reviewQueue:id";
    public static final String TAG_SEQUENCE = "sequence:tag:id";
    public static final String USER_SEQUENCE = "sequence:user:id";

    /**
     * 用户权限前缀
     */
    public static final String USER_PERMISSION = "user:permissions:";

    /**
     * 热门标签的前缀
     */
    public static final String HOT_TAG = "blog:hot:tag";
    /**
     * 用户所在的热门城市（前 15 名）
     */
    public static final String HOT_CITY_USER_IN = "location:hot:city";
    /**
     * 用户访问量
     */
    public static final String USER_ACCESS_NUM = "user:access:num";
}
