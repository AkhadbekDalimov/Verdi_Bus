package uz.asbt.digid.common.models.entity.client;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ModelAddressTemproaryAnswere {

    @JsonAlias("answere")
    private Answere answere;

    @JsonProperty("AddressTemproary")
    @JsonAlias("addressTemproary")
    private List<ModelAddressTemproary> addressTemproary;

    public Answere getAnswere() {
        return answere;
    }

    public void setAnswere(Answere answere) {
        this.answere = answere;
    }

    public List<ModelAddressTemproary> getAddressTemproary() {
        return addressTemproary;
    }

    public void setAddressTemproary(List<ModelAddressTemproary> addressTemproary) {
        this.addressTemproary = addressTemproary;
    }

    @Override
    public String toString() {
        return "ModelAddressTemproaryAnswere{" +
                "answere=" + answere +
                ", addressTemproary=" + addressTemproary +
                '}';
    }
}
