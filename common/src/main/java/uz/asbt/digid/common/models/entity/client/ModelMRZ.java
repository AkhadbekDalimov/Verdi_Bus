package uz.asbt.digid.common.models.entity.client;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ModelMRZ {

    @JsonProperty("Line1")
    @JsonAlias("line1")
    private String line1;

    @JsonProperty("Line2")
    @JsonAlias("line2")
    private String line2;

    @JsonProperty("Line3")
    @JsonAlias("line3")
    private String line3;

    @JsonProperty("Additional")
    @JsonAlias("additional")
    private String additional;

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    @Override
    public String toString() {
        return "ModelMRZ{" +
                "line1='" + line1 + '\'' +
                ", line2='" + line2 + '\'' +
                ", line3='" + line3 + '\'' +
                ", additional='" + additional + '\'' +
                '}';
    }
}
