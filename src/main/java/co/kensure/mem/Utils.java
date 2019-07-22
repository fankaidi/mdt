package co.kensure.mem;


import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;

import sun.misc.BASE64Encoder;

public class Utils {

    /**
     * 求一个字符串的md5值
     * @param target 字符串
     * @return md5 value
     */
    public static String generateMD5(String target) {
        return DigestUtils.md5Hex(target);
    }

    /**
     * 产生一个随机的salt值
     * @return salt value
     */
    public static String generateSalt(){
        Random RANDOM = new SecureRandom();
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
		String saltStr = new BASE64Encoder().encode(salt);
        return saltStr;
    }

    /**
     * 获取当前时间
     * @format 输入当前时间的格式
     * @return 对应格式值
     */
    public static String getCurrentTime(String format) {
        SimpleDateFormat f = new SimpleDateFormat(format);
        Date date = new Date();
        String returnStr = f.format(date);
        return returnStr;
    }

    public static String randomSMSCode() {
        String str = "";
        str += (int) (Math.random() * 9 + 1);
        for (int i = 0; i < 5; i++) {
            str += (int) (Math.random() * 10);
        }
        return str;
    }
    
    public static String getUUID(){
    	UUID uuid = java.util.UUID.randomUUID();
    	return uuid.toString().replace("-", "");
    }

    public static void main(String[] args) {
        System.out.println(generateSalt() );
    }

}
