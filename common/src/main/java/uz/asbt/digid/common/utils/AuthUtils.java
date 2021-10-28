package uz.asbt.digid.common.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AuthUtils {

    public static String[] parseBasicAuth(String authorization) {
        String base64Credentials = authorization.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        return credentials.split(":", 2);
    }

}
