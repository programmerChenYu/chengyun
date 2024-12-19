package com.programmercy.api.feign.user;

import com.programmercy.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Description: 远程调用 UserProfileFeignController
 * Created by 爱吃小鱼的橙子 on 2024-11-29 10:08
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@FeignClient(value = "chenyun-user", path = "/user/userProfile")
public interface UserProfileService {

    /**
     * 根据个人id，获取个人信息
     */
    @PostMapping("/getUserInfoByIdAPI")
    UserDTO getUserInfoByIdAPI(@RequestBody UserDTO userDTO);


}
