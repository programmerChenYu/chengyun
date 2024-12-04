package com.programmercy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 分页信息
 * Created by 爱吃小鱼的橙子 on 2024-11-25 16:34
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageInfoVO {

    /**
     * 当前页面
     */
    protected Long currentPage;
    /**
     * 一个页面所包含的总数
     */
    protected Long pageSize;
    /**
     * 页面总数
     */
    protected Long countOfPage;

}
