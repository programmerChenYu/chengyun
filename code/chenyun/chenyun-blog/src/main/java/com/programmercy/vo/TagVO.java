package com.programmercy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 标签VO
 * Created by 爱吃小鱼的橙子 on 2024-11-29 9:09
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagVO {

    /**
     * 标签唯一标识符
     */
    private String key;
    /**
     * 标签名称
     */
    private String tagName;
    /**
     * 标签状态（0：停用；1：启用）
     */
    private Integer tagStatus;
    /**
     * 标签创建时间
     */
    private String createTime;
    /**
     * 标签创建人
     */
    private String creator;
    /**
     * 标签使用频率
     */
    private Integer frequencyOfUse;

}
