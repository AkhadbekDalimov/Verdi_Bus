package uz.asbt.digid.common.service.logger;

public interface Exchangable<T> {
    void sendToQueue(T object);
}
