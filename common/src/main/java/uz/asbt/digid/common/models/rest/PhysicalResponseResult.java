package uz.asbt.digid.common.models.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PhysicalResponseResult {


    private String code;

    private String message;

    public PhysicalResponseResult() {
        super();
    }

    public PhysicalResponseResult(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "PhysicalResponseResult [code=" + code + ", message=" + message + "]";
    }


}
