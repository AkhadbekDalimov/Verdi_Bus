package uz.asbt.digid.crmservice.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CombineLiveness {

    private String guid;
    private String serialNumber;
    private LocalDateTime requestDate;
    private String code;
    private String message;
}
