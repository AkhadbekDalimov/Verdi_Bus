package uz.asbt.digid.updaterservice.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_NEW_VERSIONS")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class Version {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "version_generator")
  @SequenceGenerator(name = "version_generator", sequenceName = "SQ_TB_NEW_VERSIONS", allocationSize = 1)
  Long id;

  @Column(columnDefinition = "int default 0")
  Integer isDeleted;

  String currentVersion;

  @CreationTimestamp
  @Column(updatable = false)
  LocalDateTime createdDate;

  @UpdateTimestamp
  @Column(insertable = false)
  LocalDateTime updatedDate;

}
