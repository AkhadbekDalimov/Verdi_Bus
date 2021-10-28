package uz.asbt.digid.common.models.entity.client;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ModelPersonPhoto {

    @JsonProperty("Answere")
    @JsonAlias("answere")
    private Answere answere;

    @JsonProperty("PersonPhoto")
    @JsonAlias("personPhoto")
    private String personPhoto;

    @JsonProperty("Additional")
    @JsonAlias("additional")
    private String additional;

    public Answere getAnswere() {
        return answere;
    }

    public void setAnswere(Answere answere) {
        this.answere = answere;
    }

    public String getPersonPhoto() {
        return personPhoto;
    }

    public void setPersonPhoto(String personPhoto) {
        this.personPhoto = personPhoto;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    @Override
    public String toString() {
        return "ModelPersonPhoto{" +
                "answere=" + answere +
                ", personPhoto=" + personPhoto +
                ", additional='" + additional + '\'' +
                '}';
    }
}
