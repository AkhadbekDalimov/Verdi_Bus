package uz.asbt.digid.digidservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by KINS on 17.08.2021.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MobileAppIdResponseDTO {
    private boolean success;
    private String reqGUID;
}
