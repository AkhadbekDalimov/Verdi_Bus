package uz.asbt.digid.digidservice.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class WebClientRequestDTO {

  Long id;

  private String guid;

  private String mobileData;

  private String personPhoto;

  private String additional;

}
