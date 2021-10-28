package uz.asbt.digid.common.service.monitor;

public interface IMonitor<T, R> {

    void saveRequest(T request);
    void saveResponse(R response);

}
