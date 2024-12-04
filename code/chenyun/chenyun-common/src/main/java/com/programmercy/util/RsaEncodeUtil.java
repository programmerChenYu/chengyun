package com.programmercy.util;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

/**
 * Description: 文件头
 * Created by 爱吃小鱼的橙子 on 2024-11-28 11:53
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Component
@Slf4j
public class RsaEncodeUtil {

    @Resource
    Cipher cipherUtil;

    public byte[] encodeRsaMessage(byte[] message) {
        try {
            return cipherUtil.doFinal(message);
        } catch (IllegalBlockSizeException e) {
            log.error("chenyun-common:util:RsaEncodeUtil:encodeRsaMessage 出现 IllegalBlockSizeException 异常，内容如下：{}",e.getMessage());
            return null;
        } catch (BadPaddingException e) {
            log.error("chenyun-common:util:RsaEncodeUtil:encodeRsaMessage 出现 BadPaddingException 异常，内容如下：{}",e.getMessage());
            return null;
        }
    }
}
