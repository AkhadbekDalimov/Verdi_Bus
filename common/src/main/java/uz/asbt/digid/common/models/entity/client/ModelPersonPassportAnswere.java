package uz.asbt.digid.common.models.entity.client;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ModelPersonPassportAnswere {

    @JsonAlias("answere")
    private Answere answere;

    @JsonProperty("PersonPassport")
    @JsonAlias("personPassport")
    private ModelPersonPassport personPassport;

    public Answere getAnswere() {
        return answere;
    }

    public void setAnswere(Answere answere) {
        this.answere = answere;
    }

    public ModelPersonPassport getPersonPassport() {
        return personPassport;
    }

    public void setPersonPassport(ModelPersonPassport personPassport) {
        this.personPassport = personPassport;
    }

    @Override
    public String toString() {
        return "ModelPersonPassportAnswere{" +
                "answere=" + answere +
                ", personPassport=" + personPassport +
                '}';
    }
}
