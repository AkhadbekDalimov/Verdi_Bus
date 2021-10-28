package uz.asbt.digid.digidservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "INN")
public class Inn implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "INN_ID")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inn_seq")
  @SequenceGenerator(name = "inn_seq", sequenceName = "SQ_INN", allocationSize = 1)
  private Long id;

  @Column(name = "INN")
  private String inn;

  @Column(name = "INN_DATE")
  private String innDate;

  @Column(name = "TAX_CODE")
  private String taxCode;

  @Lazy
  @ManyToOne
  @JoinColumn(name = "PINPP_ID")
  private Pinpp pinpp;
}