package uz.asbt.digid.crmservice.model;

import lombok.Data;

@Data
public class Report {

    private String deviceId;
    private String requestsQuantity;
    private String responceQuantity;
    private String successResponceQuantity;
}
