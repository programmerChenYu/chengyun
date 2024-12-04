package com.programmercy.util;

import com.alibaba.druid.filter.config.ConfigTools;

/**
 * Description: 文件头
 * Created by 爱吃小鱼的橙子 on 2024-11-08 14:49
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
public class Encrypt {

    /**
     * 数据源密码加密
     * @throws Exception
     */
    public static void druidEncrypt() throws Exception {
        // 密码明文
        String password = "123456";
        String[] keyPair = ConfigTools.genKeyPair(512);
        // 私钥
        String privateKey = keyPair[0];
        // 公钥
        String publicKey = keyPair[1];

        // 用私钥加密后的密文
        password = ConfigTools.encrypt(privateKey, password);
        System.out.println("privateKey:" + privateKey);
        System.out.println("publicKey:" + publicKey);

        System.out.println("password:" + password);
        String decryptPassword = ConfigTools.decrypt(publicKey, password);
        System.out.println("解密后:" + decryptPassword);
    }

    public static void main(String[] args) throws Exception {
        druidEncrypt();
    }
}
