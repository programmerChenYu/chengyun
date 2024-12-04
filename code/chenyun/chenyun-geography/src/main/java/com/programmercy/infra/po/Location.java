package com.programmercy.infra.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 地理位置表(Location)实体类
 * @author 爱吃小鱼的橙子
 * @since 2024-11-14 14:15:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location implements Serializable {
    private static final long serialVersionUID = 637708235712041460L;
    /**
     * 位置唯一标识
     */
    private Long locationId;
    /**
     * 国家
     */
    private String country;
    /**
     * 省份或地区
     */
    private String rejion;
    /**
     * 城市
     */
    private String city;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 经度
     */
    private String longitude;

}

