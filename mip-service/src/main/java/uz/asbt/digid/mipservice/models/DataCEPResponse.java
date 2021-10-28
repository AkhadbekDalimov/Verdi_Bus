package uz.asbt.digid.mipservice.models;

/**
 * Created by KINS on 27.02.2020.
 */

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "datacepresponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class DataCEPResponse {

    @XmlElement(name = "row")
    @JsonProperty("row")
    public DataCEPResponseRow row;

    @Override
    public String toString() {
        return "DataCEPResponse{" +
                "row=" + row +
                '}';
    }
}
