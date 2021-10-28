package uz.asbt.digid.digidservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;
import uz.asbt.digid.common.models.entity.Client;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "DOC_READ_DATA")
public class DocReadData implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "DOC_READ_DATA_ID")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doc_read_data_seq")
  @SequenceGenerator(name = "doc_read_data_seq", sequenceName = "SQ_DOC_READ_DATA", allocationSize = 1)
  private Long id;

  @Column(name = "READ_DATE")
  private String readDate;

  @Lazy
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CLIENT_ID")
  private Client client;

  @Lazy
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PASSPORT_DATA_ID")
  private PassportData passportData;
}
