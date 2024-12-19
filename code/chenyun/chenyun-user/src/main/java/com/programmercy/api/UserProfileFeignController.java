package com.programmercy.api;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.programmercy.constant.ExceptionLanguageConstant;
import com.programmercy.domain.service.UserProfileServiceDomain;
import com.programmercy.dto.UserDTO;
import com.programmercy.entity.Result;
import com.programmercy.vo.UserVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

/**
 * Description: 提供远程调用服务的用户个人资料控制类
 * Created by 爱吃小鱼的橙子 on 2024-11-29 9:53
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@RestController
@RequestMapping("/userProfile")
@Slf4j
public class UserProfileFeignController {

    @Resource
    private UserProfileServiceDomain userProfileServiceDomain;

    /**
     * 根据个人id，获取个人信息
     */
    @PostMapping("/getUserInfoByIdAPI")
    public UserDTO getUserInfoByIdAPI(@RequestBody UserDTO userDTO) {
        try {
            Preconditions.checkNotNull(userDTO.getKey(), ExceptionLanguageConstant.USER_ID_NULL_EXCEPTION);
            if (log.isInfoEnabled()) {
                log.info("chenyun-user:api:UserProfileController:getUserInfoByIdAPI:userDTO: [{}]", JSON.toJSONString(userDTO));
            }
            UserVO userVO = userProfileServiceDomain.getUserInfoById(Long.valueOf(userDTO.getKey()));
            BeanUtils.copyProperties(userVO, userDTO);
            return userDTO;
        } catch (Exception e) {
            log.error("chenyun-user:api:UserProfileController:getUserInfoByIdAPI:Exception: [{},{}]", e.getMessage(), e.getStackTrace());
            return null;
        }
    }

}
