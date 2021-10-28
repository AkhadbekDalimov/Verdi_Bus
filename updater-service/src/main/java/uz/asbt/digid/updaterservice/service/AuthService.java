package uz.asbt.digid.updaterservice.service;

/**
 * Created by User on 17.01.2020.
 */
public interface AuthService {


    void checkSign(final Object object) throws Exception;

    String generateSign(final Object object) throws Exception;

}
