package uz.asbt.digid.common.models.entity.client;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ModelMRZAnswere {

    @JsonAlias("answere")
    private Answere answere;

    @JsonProperty("MRZ")
    @JsonAlias("mrz")
    private ModelMRZ mrz;

    public Answere getAnswere() {
        return answere;
    }

    public void setAnswere(Answere answere) {
        this.answere = answere;
    }

    public ModelMRZ getMrz() {
        return mrz;
    }

    public void setMrz(ModelMRZ mrz) {
        this.mrz = mrz;
    }

    @Override
    public String toString() {
        return "ModelMRZAnswere{" +
                "answere=" + answere +
                ", mrz=" + mrz +
                '}';
    }
}
