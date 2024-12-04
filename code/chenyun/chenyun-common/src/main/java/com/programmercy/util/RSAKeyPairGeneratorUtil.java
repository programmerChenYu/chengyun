package com.programmercy.util;

import com.programmercy.constant.RSAKeyConstant;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

/**
 * Description: RSA公私钥生成
 * Created by 爱吃小鱼的橙子 on 2024-11-28 11:08
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
public class RSAKeyPairGeneratorUtil {

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048); // 可以根据需要调整密钥大小
        return keyGen.generateKeyPair();
    }

    public static String getPublicKeyString(PublicKey publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public static String getPrivateKeyString(PrivateKey privateKey) {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
//        KeyPair keyPair = generateKeyPair();
//        System.out.println("公钥："+keyPair.getPublic());
//        System.out.println("私钥："+keyPair.getPrivate());
//        System.out.println("==============================");
//        System.out.println("base64公钥："+getPublicKeyString(keyPair.getPublic()));
//        System.out.println("base64私钥："+getPrivateKeyString(keyPair.getPrivate()));
    }
}
