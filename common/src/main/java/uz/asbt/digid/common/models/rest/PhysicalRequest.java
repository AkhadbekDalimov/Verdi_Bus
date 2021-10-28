package uz.asbt.digid.common.models.rest;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhysicalRequest {
    PhysicalRequestHeader header;
    PhysicalRequestBody body;
    String guid;
    String serialNumber;
}
