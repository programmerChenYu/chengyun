package com.programmercy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 标签搜索的接收参数
 * Created by 爱吃小鱼的橙子 on 2024-11-29 17:16
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagingQuerySearchTagListVO extends PageInfoVO {

    /**
     * 搜索的标签名
     */
    private String tagName;
    /**
     * 搜索的标签状态
     */
    private String tagStatus;
}
