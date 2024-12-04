package com.programmercy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Description: 标签批量操作
 * Created by 爱吃小鱼的橙子 on 2024-11-29 13:56
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagBatchOptionVO {

    /**
     * 批量操作选中的标签
     */
    private List<String> tagIds;

}
