package uz.asbt.digid.common.models.entity.client;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ModelPersonIdCardAnswere {

    @JsonAlias("answere")
    private Answere answere;

    @JsonProperty("PersonIdCard")
    @JsonAlias("personIdCard")
    private ModelPersonIdCard personIdCard;

    public Answere getAnswere() {
        return answere;
    }

    public void setAnswere(Answere answere) {
        this.answere = answere;
    }

    public ModelPersonIdCard getPersonIdCard() {
        return personIdCard;
    }

    public void setPersonIdCard(ModelPersonIdCard personIdCard) {
        this.personIdCard = personIdCard;
    }

    @Override
    public String toString() {
        return "ModelPersonIdCardAnswere{" +
                "answere=" + answere +
                ", personIdCard=" + personIdCard +
                '}';
    }
}
