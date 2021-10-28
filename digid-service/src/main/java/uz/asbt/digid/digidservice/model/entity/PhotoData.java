package uz.asbt.digid.digidservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "PHOTO_DATA")
public class PhotoData implements Serializable {

  private static final long serialVersionUID = 1L;

  public PhotoData(final String photo) {
    this.photo = photo;
  }

  @Id
  @Column(name = "PHOTO_DATA_ID")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "photo_data_seq")
  @SequenceGenerator(name = "photo_data_seq", sequenceName = "SQ_PHOTO_DATA", allocationSize = 1)
  private Long id;

  @Column(name = "PHOTO")
  private String photo;

  @Lazy
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PASSPORT_DATA_ID")
  private PassportData passportData;

}
