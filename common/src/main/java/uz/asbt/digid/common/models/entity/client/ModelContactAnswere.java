package uz.asbt.digid.common.models.entity.client;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ModelContactAnswere {

    @JsonAlias("answere")
    private Answere answere;

    @JsonProperty("Contacts")
    @JsonAlias("contacts")
    private List<ModelContact> contacts;

    public Answere getAnswere() {
        return answere;
    }

    public void setAnswere(Answere answere) {
        this.answere = answere;
    }

    public List<ModelContact> getContacts() {
        return contacts;
    }

    public void setContacts(List<ModelContact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "ModelContactAnswere{" +
                "answere=" + answere +
                ", contacts=" + contacts +
                '}';
    }
}
