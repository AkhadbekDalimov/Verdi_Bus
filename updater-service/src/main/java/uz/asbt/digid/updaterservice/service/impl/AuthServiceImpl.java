package uz.asbt.digid.updaterservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import uz.asbt.digid.updaterservice.repository.ClientRepository;
import uz.asbt.digid.updaterservice.repository.DeviceRepository;
import uz.asbt.digid.updaterservice.service.AuthService;
import uz.asbt.digid.updaterservice.util.CryptoUtil;
import uz.asbt.digid.common.exception.CustomException;
import uz.asbt.digid.common.models.entity.Client;
import uz.asbt.digid.common.models.entity.Device;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by User on 17.01.2020.
 */
@Service
public class AuthServiceImpl implements AuthService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment env;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public void checkSign(final Object object) throws Exception {
        logger.info("AuthService -> checkSign() -> object: {}", object);


        SortedMap<String, String> sorted = mapEntityToSortedMap(object);

        String ipAddress = "";
        String macAddress = "";
        String serialNumber = null;
        String sign = null;

        for (Map.Entry<String, String> entry : sorted.entrySet()){
            if ((entry.getKey()).equals("sign")){
                sign = entry.getValue();
            }else if ((entry.getKey()).equals("ipAddress")){
                ipAddress = entry.getValue();
            }else if ((entry.getKey()).equals("macAddress")){
                macAddress = entry.getValue();
            }else if ((entry.getKey()).equals("serialNumber")){
                serialNumber = entry.getValue();
            }
        }

        logger.info("AuthService -> checkSign() -> ipAddress: {}", ipAddress);
        logger.info("AuthService -> checkSign() -> macAddress: {}", macAddress);
        logger.info("AuthService -> checkSign() -> serialNumber: {}", serialNumber);
        logger.info("AuthService -> checkSign() -> sign: {}", sign);

        if (sign == null || serialNumber == null){
            throw new CustomException(env.getProperty("code_1009", Integer.class),
                    env.getProperty("message_1009"));
        }


        Client client = clientRepository.
                findClientByDeviceSerialNumber(serialNumber)
                .orElseThrow(() -> new CustomException(env.getProperty("code_1011", Integer.class),
                        env.getProperty("message_1011")));

        Device device = deviceRepository.findDeviceBySerialNumber(serialNumber)
                .orElseThrow(() -> new CustomException(env.getProperty("code_1012", Integer.class),
                        env.getProperty("message_1012")));

        StringBuilder concat = new StringBuilder();

        concat.append(client.getIpAddress());
        concat.append(client.getMacAddress());
        concat.append(device.getSerialNumber());
        concat.append(client.getClientPubKey());

        String signature = CryptoUtil.getMd5Hash(concat.toString());

        if (!sign.equals(signature)){
            throw new CustomException(env.getProperty("code_1010", Integer.class),
                    env.getProperty("message_1010"));
        }
    }

    @Override
    public String generateSign(Object object) throws Exception {
        logger.info("AuthService -> generateSign() -> object: {}", object);


        SortedMap<String, String> sorted = mapEntityToSortedMap(object);

        String ipAddress = "";
        String macAddress = "";
        String serialNumber = null;
        String sign = null;

        for (Map.Entry<String, String> entry : sorted.entrySet()){
            if ((entry.getKey()).equals("sign")){
                sign = entry.getValue();
            }else if ((entry.getKey()).equals("ipAddress")){
                ipAddress = entry.getValue();
            }else if ((entry.getKey()).equals("macAddress")){
                macAddress = entry.getValue();
            }else if ((entry.getKey()).equals("serialNumber")){
                serialNumber = entry.getValue();
            }
        }

        if (sign == null || serialNumber == null){
            throw new CustomException(env.getProperty("code_1009", Integer.class),
                    env.getProperty("message_1009"));
        }

        Client client = clientRepository.
                findClientByDeviceSerialNumber(serialNumber)
                .orElseThrow(() -> new CustomException(env.getProperty("code_1011", Integer.class),
                        env.getProperty("message_1011")));

        StringBuilder concat = new StringBuilder();

        concat.append(ipAddress);
        concat.append(macAddress);
        concat.append(serialNumber);
        concat.append(client.getClientPubKey());

        return CryptoUtil.getMd5Hash(concat.toString());
    }

    public SortedMap<String,String> mapEntityToSortedMap(final Object object) throws IllegalAccessException {
        SortedMap<String, Field> fields = new TreeMap<>();
        Class clazz  = object.getClass();
        while(!clazz.equals(Object.class)){
            for (Field f : clazz.getDeclaredFields()) {
                if (!Modifier.isStatic(f.getModifiers())) {
                    fields.put(f.getName(), f);
                }
            }
            clazz = clazz.getSuperclass();
        }

        SortedMap<String,String> resultMap = new TreeMap<>();
        for (Field f : fields.values()) {
            f.setAccessible(true);
            resultMap.put(f.getName(),f.get(object).toString());
        }
        return resultMap;
    }

}
