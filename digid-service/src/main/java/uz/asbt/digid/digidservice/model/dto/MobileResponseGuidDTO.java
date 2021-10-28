package uz.asbt.digid.digidservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MobileResponseGuidDTO {
    private boolean success;
    private Integer code;
    private String message;
}
