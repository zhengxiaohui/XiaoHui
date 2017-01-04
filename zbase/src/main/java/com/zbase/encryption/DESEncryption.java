package com.zbase.encryption;

import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 * DES加密，可双向加密，解密，可用于本地文件或者http传输时使用
 *
 * @author z
 */
public class DESEncryption {
    public static final String TAG_ENCRYPT = "cpvscpvs";//和解密端（通常是服务端）约定的秘钥,长度必须是8的整数

    public static String encryptDES(String encryptString) {
        try {
            byte[] result = trides_crypt(TAG_ENCRYPT.getBytes("GBK"), encryptString.getBytes("GBK"));
            String encry = bytesToHexString(result);
            return encry;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decryptDES(String decryptString) {
        try {
            byte[] dest = hexStringToByte(decryptString);
            byte[] src = trides_decrypt(TAG_ENCRYPT.getBytes("UTF-8"), dest);
            src = cutZeroToByte(src);

            return new String(src, "GBK");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] trides_crypt(byte key[], byte data[]) {
        try {
            byte[] k = new byte[24];

            int len = data.length;
            if (data.length % 8 != 0) {
                len = data.length - data.length % 8 + 8;
            }
            byte[] needData = null;
            if (len != 0)
                needData = new byte[len];

            for (int i = 0; i < len; i++) {
                needData[i] = 0x00;
            }

            System.arraycopy(data, 0, needData, 0, data.length);

            if (key.length == 16) {
                System.arraycopy(key, 0, k, 0, 8);
                System.arraycopy(key, 0, k, 16, 8);
            } else if (key.length == 8) {
                System.arraycopy(key, 0, k, 0, 8);
                System.arraycopy(key, 0, k, 8, 8);
                System.arraycopy(key, 0, k, 16, 8);
            } else {
                System.arraycopy(key, 0, k, 0, 24);
            }

            KeySpec ks = new DESedeKeySpec(k);
            SecretKeyFactory kf = SecretKeyFactory.getInstance("DESede");
            SecretKey ky = kf.generateSecret(ks);

            Cipher c = Cipher.getInstance("DESede/ECB/NoPadding");
            c.init(Cipher.ENCRYPT_MODE, ky);
            byte[] result = c.doFinal(needData);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] trides_decrypt(byte key[], byte data[]) {
        try {
            byte[] k = new byte[24];
            int len = data.length;
            if (data.length % 8 != 0) {
                len = data.length - data.length % 8 + 8;
            }
            byte[] needData = null;
            if (len != 0)
                needData = new byte[len];

            for (int i = 0; i < len; i++) {
                needData[i] = 0x00;
            }

            System.arraycopy(data, 0, needData, 0, data.length);

            if (key.length == 16) {
                System.arraycopy(key, 0, k, 0, key.length);
                System.arraycopy(key, 0, k, 16, 8);
            } else if (key.length == 8) {
                System.arraycopy(key, 0, k, 0, 8);
                System.arraycopy(key, 0, k, 8, 8);
                System.arraycopy(key, 0, k, 16, 8);
            } else {
                System.arraycopy(key, 0, k, 0, 24);
            }

            KeySpec ks = new DESedeKeySpec(k);
            SecretKeyFactory kf = SecretKeyFactory.getInstance("DESede");
            SecretKey ky = kf.generateSecret(ks);

            Cipher c = Cipher.getInstance("DESede/ECB/NoPadding");
            c.init(Cipher.DECRYPT_MODE, ky);
            byte[] result = c.doFinal(needData);
            return result;
            //return new String(result,"GBK").getBytes("GBK");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] cutZeroToByte(byte[] src) {
        int pos = -1;
        for (int i = src.length - 1; i >= 0; i--) {
            if (src[i] == 0)
                pos = i;
            else
                break;
        }
        if (pos > 0 && pos < src.length) {
            byte[] dest = new byte[pos];
            System.arraycopy(src, 0, dest, 0, pos);
            return dest;
        }
        return src;
    }

    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static byte toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }

    public static final String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1)
                hs += ("0" + stmp);
            else
                hs += stmp;
        }
        return hs.toUpperCase();
    }

}
