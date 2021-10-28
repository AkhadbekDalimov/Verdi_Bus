package uz.asbt.digid.digidservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import uz.asbt.digid.digidservice.util.annotations.WebToken;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * @author Salvadore
 * @version 1.0.0
 * @since 01.07.2017
 * This class provide simply methods for working with <b>Cookies</b>, <b>Tokens</b>
 * and <b>Hash</b>.
 * This Class is component of the <b>Spring Framework</b>.
 */
@Component
public class WebUtils {

    private static final Logger log = LoggerFactory.getLogger(WebUtils.class);

    /**
     * Method need for parse HttpServletRequest and
     * return the String representation of cookie value by key.
     * @param request HttpServletRequest
     * @param key of the cookie
     * @return cookie value
     * @throws NullPointerException
     */
    public String getCookieValue(final HttpServletRequest request, final String key)
            throws NullPointerException {
        for (final Cookie c : request.getCookies()) {
            if (c.getName().equals(key)) {
                return c.getValue();
            }
        }
        return "";
    }

    public String getCookieValueWithoutException(final HttpServletRequest request, final String key) {
        if (request.getCookies() == null) {
            return "";
        }
        for (Cookie c : request.getCookies()) {
            if (c.getName().equals(key)) {
                return c.getValue();
            }
        }
        return "";
    }

    /**
     * Overloading method {@link #getCookieValue(HttpServletRequest, String)}
     * @param cookies the array of cookie
     * @param key searched key in cookie
     * @return cookie value
     * @throws NullPointerException
     */
    public String getCookieValue(final Cookie[] cookies, final String key)
            throws NullPointerException {
        for (final Cookie c : cookies) {
            if (c.getName().equals(key)) {
                return c.getValue();
            }
        }
        return "";
    }

    /**
     * Method returned the object of cookie searched by key
     * @param cookies array of cookie objects
     * @param key searched key in cookies
     * @return cookie object
     * @throws NullPointerException
     */
    public Cookie getCookie(final Cookie[] cookies, final String key)
            throws NullPointerException {
        if (cookies != null) {
            for (final Cookie c : cookies) {
                if (c.getName().equals(key)) {
                    return c;
                }
            }
        }
        return null;
    }

    /**
     * Overloading method {@link #getCookie(Cookie[], String)}
     * @param request object of HttpServletRequest
     * @param key searched key in cookies
     * @return cookie object
     * @throws NullPointerException
     */
    public Cookie getCookie(final HttpServletRequest request, final String key)
            throws NullPointerException {
        for (final Cookie c : request.getCookies()) {
            if (c.getName().equals(key)) {
                return c;
            }
        }
        return null;
    }

    /**
     * This method generate string token from given object.
     * This method uses a JWT library to generate token.
     * @param subject the subject of created token
     * @param secretKey secret key for encryption the token
     * @param expiration time in second for token expiration.
     * @param obj object which need to generate
     * @param <T> Generic type
     * @return
     */
    public <T> String generateToken(final String subject,
                                    final String secretKey,
                                    final long expiration,
                                    final T obj) {
        try {
            final Class<?> c = Class.forName(obj.getClass().getName());
            final Claims claims = Jwts.claims().setSubject(subject);
            claims.put("expiration", (new Date(new Date().getTime() + expiration * 1000)));
            for(final Field f : c.getDeclaredFields()) {
                f.setAccessible(true);
                final WebToken annotation = f.getAnnotation(WebToken.class);
                if (annotation != null) {
                    claims.put(annotation.name(), f.get(obj));
                }
            }
            return Jwts.builder()
                    .setClaims(claims)
                    .signWith(SignatureAlgorithm.HS512, secretKey)
                    .compact();
        } catch (final ClassNotFoundException | IllegalAccessException | SecurityException e) {
            log.error(e.getMessage());
        }
        return "";
    }

    /**
     * This method parse the given token and return the needle class object.
     * @param secretKey needs to parse token
     * @param token content
     * @param clazz Class instance
     * @param <T> Generic type
     * @return needle class object
     * @throws UnsupportedEncodingException
     */
    @SuppressWarnings("unchecked")
    public <T> T parseToken(final String secretKey, final String token, final Class<?> clazz) {
        try {
            final Claims body = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
            final long expiration = (Long) body.get("expiration");
            if (!((expiration - new Date().getTime()) > 0)) {
                log.error("Token is expired");
                return null;
            }
            final Object u = clazz.newInstance();
            for (final Field f : clazz.getDeclaredFields()) {
                f.setAccessible(true);
                final WebToken annotation = f.getAnnotation(WebToken.class);
                if (annotation != null) {
                    f.set(u, body.get(annotation.name()));
                }
            }
            return (T)u;
        } catch (final JwtException | ClassCastException | IllegalAccessException | InstantiationException ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    /**
     * Method generate MD5 string from given string.
     * @param inputString string needs to convert
     * @return MD5 string
     * @throws NoSuchAlgorithmException
     */
    public String getMD5Hash(final String inputString) throws NoSuchAlgorithmException {
        final MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(inputString.getBytes());
        final byte[] diegest = md.digest();
        return convertToByte(diegest);
    }

    /**
     * Private method generate string from byte array
     * @param byteData byte array
     * @return MD5 string
     */
    private static String convertToByte(final byte[] byteData) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            builder.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return builder.toString();
    }

    /**
     * Метод создает объект Cookie с переданными параметрами.
     * @param key ключ для cookie
     * @param value значение ключа cookie
     * @param path домен куда установятся cookie, если пустая строка то домен устанавливается по умолчанию.
     * @param seconds время жизни cookie.
     * @return возвращает сформированный объект.
     */
    public Cookie setDataInCookies(final String key,
                                   final String value,
                                   final String path,
                                   final int seconds,
                                   final boolean isHttpOnly) {
        final Cookie cookie = new Cookie(key, value);
        cookie.setPath(path);
        cookie.setMaxAge(seconds);
        if (isHttpOnly) {
            cookie.setHttpOnly(true);
        } else {
            cookie.setHttpOnly(false);
        }
        return cookie;
    }

}

