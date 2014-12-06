/**
 * @Title: DesUtil.java
 * @Copyright (C) 2014 畅游时代
 * @Description:
 * @Revision History:
 * @Revision 1.0 2014-1-15  闫昆
 */

package com.changyou.mgp.sdk.mbi.utils;

import java.security.Key;
import java.security.Security;
import java.util.UUID;

import javax.crypto.Cipher;

/**
 * @ClassName: DesUtil
 * @Description: DES对称加密
 * @author <a href="mailto:yankun@cyou-inc.com">闫昆</a>于 2014-1-15 下午1:28:59
 */

public class DesUtil {

    /**
     * @Fields defaultKey : 默认密钥
     */
    private static String defaultKey = "cyou-mrd";

    /**
     * @Fields encryptCipher : Cipher
     */
    private Cipher encryptCipher = null;

    /**
     * @Fields decryptCipher : Cipher
     */
    private Cipher decryptCipher = null;

    /**
     * 创建一个新的实例 DesUtil，使用默认密钥
     * @throws Exception
     */
    public DesUtil() throws Exception {
        this(defaultKey);
    }

    /**
     * 创建一个新的实例 DesUtil，指定密钥构造方法
     * @param seed 指定的密钥
     * @throws Exception
     */
    public DesUtil(String seed) throws Exception {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        Key key = getKey(seed.getBytes());

        encryptCipher = Cipher.getInstance("DES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);

        decryptCipher = Cipher.getInstance("DES");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
    }

    /**
     * 将byte数组转换为表示16进制值的字符串
     * @param arr 需要转换的byte数组
     * @return 转换后的字符串
     * @throws Exception exception
     */
    public static String byteArr2HexStr(byte[] arr) throws Exception {
        int iLen = arr.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arr[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    /**
     * 将表示16进制值的字符串转换为byte数组
     * @param str 需要转换的字符串
     * @return 转换后的byte数组
     * @throws Exception exception
     */
    public static byte[] hexStr2ByteArr(String str) throws Exception {
        byte[] arrB = str.getBytes();
        int iLen = arrB.length;

        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    /**
     * 加密字节数组
     * @param arr 需加密的字节数组
     * @return 加密后的字节数组
     * @throws Exception exception
     */
    public byte[] encrypt(byte[] arr) throws Exception {
        return encryptCipher.doFinal(arr);
    }

    /**
     * 加密字符串
     * @param str 需加密的字符串
     * @return 加密后的字符串
     * @throws Exception exception
     */
    public String encrypt(String str) throws Exception {
        return byteArr2HexStr(encrypt(str.getBytes()));
    }

    /**
     * 解密字节数组
     * @param arr 需解密的字节数组
     * @return 解密后的字节数组
     * @throws Exception exception
     */
    public byte[] decrypt(byte[] arr) throws Exception {
        return decryptCipher.doFinal(arr);
    }

    /**
     * 解密字符串
     * @param str 需解密的字符串
     * @return 解密后的字符串
     * @throws Exception exception
     */
    public String decrypt(String str) throws Exception {
        return new String(decrypt(hexStr2ByteArr(str)));
    }

    /**
     * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
     * @param arr 构成该字符串的字节数组
     * @return 生成的密钥
     * @throws Exception exception
     */
    private Key getKey(byte[] arr) throws Exception {
        // 创建一个空的8位字节数组（默认值为0）
        byte[] arrB = new byte[8];

        // 将原始字节数组转换为8位
        for (int i = 0; i < arr.length && i < arrB.length; i++) {
            arrB[i] = arr[i];
        }

        // 生成密钥
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

        return key;
    }

    public static void main(String[] args) throws Exception {
        String key = UUID.randomUUID().toString();
        System.out.println(key);
        DesUtil des = new DesUtil();
        String str = des.encrypt("sfsfsfsfd");
        System.out.println(str);
        System.out.println(des.decrypt(str));
    }
}
