package uz.asbt.digid.common.service.monitor;

public interface ILiveness <T, R> {

    void saveRequest(T request);
    void saveResponse(R response);

}
