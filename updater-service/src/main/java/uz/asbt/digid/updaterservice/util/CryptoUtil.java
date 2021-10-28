package uz.asbt.digid.updaterservice.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by User on 17.01.2020.
 */
public class CryptoUtil {

    public static String getMd5Hash(String message) throws NoSuchAlgorithmException {
        StringBuilder hexString = new StringBuilder();
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(message.getBytes());
        byte[] hash = md.digest();

        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10) {
                hexString.append("0").append(Integer.toHexString((0xFF & hash[i])));
            } else {
                hexString.append(Integer.toHexString(0xFF & hash[i]));
            }
        }
        return hexString.toString();
    }

}
