package com.programmercy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 认证 VO
 * Created by 爱吃小鱼的橙子 on 2024-12-16 16:11
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthVO {

    /**
     * token 令牌
     */
    private String token;
}
