package uz.asbt.digid.common.models.entity.client;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ModelAddressAnswere {

    @JsonAlias("answere")
    private Answere answere;

    @JsonProperty("Address")
    @JsonAlias("modelAddress")
    private ModelAddress modelAddress;

    public Answere getAnswere() {
        return answere;
    }

    public void setAnswere(Answere answere) {
        this.answere = answere;
    }

    public ModelAddress getModelAddress() {
        return modelAddress;
    }

    public void setModelAddress(ModelAddress modelAddress) {
        this.modelAddress = modelAddress;
    }

    @Override
    public String toString() {
        return "ModelAddressAnswere{" +
                "answere=" + answere +
                ", modelAddress=" + modelAddress +
                '}';
    }
}
