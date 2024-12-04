package com.programmercy.infra.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 地理位置表(Location)实体类
 *
 * @author makejava
 * @since 2024-11-18 13:46:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location implements Serializable {
    private static final long serialVersionUID = -18829007435584569L;
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
    private String region;
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

