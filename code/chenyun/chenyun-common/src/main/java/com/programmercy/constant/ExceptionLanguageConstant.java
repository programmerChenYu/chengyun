package com.programmercy.constant;

/**
 * Description: 统一的异常话术
 * Created by 爱吃小鱼的橙子 on 2024-11-29 9:11
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
public class ExceptionLanguageConstant {

    public static final String PAGE_SIZE_EXCEPTION = "pageSize 参数异常，小于等于 0";
    public static final String CURRENT_PAGE_EXCEPTION = "currentPage 参数异常，小于等于 0";
    public static final String ARGUMENT_NULL_EXCEPTION = "参数为空异常，参数不可为空";
    public static final String USER_ID_NULL_EXCEPTION = "用户 ID 异常，用户 ID 为空";
    public static final String TAG_ID_NULL_EXCEPTION = "标签 ID 异常，标签 ID 为空";
    public static final String TAG_NAME_NULL_EXCEPTION = "标签命名异常，标签名称为空";
    public static final String BATCH_TAG_ID_NULL_EXCEPTION = "标签批量操作异常，没有选中标签";
    public static final String SEARCH_TAG_NAME_AND_STATUS_NULL_EXCEPTION = "标签搜索异常，标签名和标签状态不能同时为空";
    public static final String SEARCH_ARTICLE_TITLE_AND_STATUS_NULL_EXCEPTION = "文章搜索异常，文章题目和文章状态不能同时为空";
    public static final String BLOG_ID_NULL_EXCEPTION = "博客 ID 异常，博客 ID 为空";
}
