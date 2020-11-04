package web.service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class DESUtil {
    /*
     * 生成密钥
     */
    public static byte[] initKey() throws Exception{
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        keyGen.init(56);
        SecretKey secretKey = keyGen.generateKey();
        return secretKey.getEncoded();
    }


    /*
     * DES 加密
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception{
        SecretKey secretKey = new SecretKeySpec(key, "DES");

        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] cipherBytes = cipher.doFinal(data);
        return cipherBytes;
    }


    /*
     * DES 解密
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception{
        SecretKey secretKey = new SecretKeySpec(key, "DES");

        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] plainBytes = cipher.doFinal(data);
        return plainBytes;
    }

    //Test
    public static void main(String[] args) throws Exception {
        //byte[] desKey = DESUtil.initKey();
        byte[] desKey = "HelloWor".getBytes("ISO-8859-1");
        System.out.println(System.getProperty("file.encoding"));
        System.out.println("DES KEY : " + new String(desKey));
        byte[] desResult = DESUtil.encrypt("DATA1".getBytes("ISO-8859-1"), desKey);
        System.out.println(desResult.length);
        System.out.println(">>>DES 加密结果>>>" + new String(desResult));
        final Base64.Encoder encoder = Base64.getEncoder();
        final String encodedText = encoder.encodeToString(desResult);
        final Base64.Decoder decoder = Base64.getDecoder();
        byte[] a = decoder.decode(encodedText);
        System.out.println(a.length);

        byte[] desPlain = DESUtil.decrypt(a, desKey);
        System.out.println( ">>>DES 解密结果>>>" + new String(desPlain));
    }
}
