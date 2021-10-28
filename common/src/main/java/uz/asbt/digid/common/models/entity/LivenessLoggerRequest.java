package uz.asbt.digid.common.models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_NEW_LIVENESS_REQUEST",
  indexes = {
    @Index(name = "IDX_REQ_DATE", columnList = "REQUEST_DATE"),
    @Index(name = "IDX_SN", columnList = "SERIAL_NUMBER")
  })
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LivenessLoggerRequest {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "liveness_request_generator")
  @SequenceGenerator(name = "liveness_request_generator", sequenceName = "SQ_TB_NEW_LIVENESS_REQUEST", allocationSize = 1)
  private long id;

  @Column(name = "GUID", nullable = false)
  private String guid;

  @Column(name = "SERIAL_NUMBER", nullable = false)
  private String serialNumber;

  @Column(name = "PINPP")
  private String pinpp;

  @Column(name = "DOCUMENT_NUMBER", nullable = false)
  private String documentNumber;

  @Column(name = "REQUEST_DATE")
  private LocalDateTime requestDate;

  @Lob
  @Column(name = "PHOTO")
  private String photo;

  @Lob
  @Column(name = "ADDITIOANL_PHOTO")
  private String additionalPhoto;

}
