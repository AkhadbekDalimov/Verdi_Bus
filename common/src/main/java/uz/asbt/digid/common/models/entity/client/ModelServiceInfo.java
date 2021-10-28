package uz.asbt.digid.common.models.entity.client;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ModelServiceInfo {

    @JsonProperty("Answere")
    @JsonAlias("answere")
    private Answere answere;

    @JsonProperty("ServiceInfo")
    @JsonAlias("serviceInfo")
    private ServiceInfo serviceInfo;

    public Answere getAnswere() {
        return answere;
    }

    public void setAnswere(Answere answere) {
        this.answere = answere;
    }

    public ServiceInfo getServiceInfo() {
        return serviceInfo;
    }

    public void setServiceInfo(ServiceInfo serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    @Override
    public String toString() {
        return "ModelServiceInfo{" +
                "answere=" + answere +
                ", serviceInfo=" + serviceInfo +
                '}';
    }
}
