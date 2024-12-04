package com.programmercy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Description: 分页查询标签列表VO
 * Created by 爱吃小鱼的橙子 on 2024-11-29 9:06
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagingQueryTagListVO extends PageInfoVO {

    /**
     * 标签列表
     */
    private List<TagVO> tagList;

}
