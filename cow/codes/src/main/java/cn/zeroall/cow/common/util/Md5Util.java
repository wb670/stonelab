package cn.zeroall.cow.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.ArrayUtils;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Apr 30, 2009
 */
public class Md5Util {

    private static MessageDigest md;

    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            // ignore
        }
    }

    public static String encrypt(String... values) {
        if (ArrayUtils.isEmpty(values)) {
            return null;
        }
        for (String value : values) {
            md.update(value.getBytes());
        }
        byte[] ret = md.digest();
        return String.valueOf(Hex.encodeHex(ret));
    }

}
