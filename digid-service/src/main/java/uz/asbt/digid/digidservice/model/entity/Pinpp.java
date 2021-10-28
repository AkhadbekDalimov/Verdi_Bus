package uz.asbt.digid.digidservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "PINPP")
public class Pinpp implements Serializable {

  private static final long serialVersionUID = 1L;
  
  @Id
  @Column(name = "PINPP_ID")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pinpp_seq")
  @SequenceGenerator(name = "pinpp_seq", sequenceName = "SQ_PINPP", allocationSize = 1)
  private Long id;

  @Column(name = "PINPP")
  private String pinpp;

}
