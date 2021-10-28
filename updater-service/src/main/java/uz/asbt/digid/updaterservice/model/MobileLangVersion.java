package uz.asbt.digid.updaterservice.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "TB_MOBILE_LANG_VERSION")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MobileLangVersion {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mob_lang_version_generator")
  @SequenceGenerator(name = "mob_lang_version_generator", sequenceName = "SQ_TB_MOBILE_LANG_VERSION", allocationSize = 1)
  Long id;

  @Column(name = "VERSION")
  String version;

  @Column(name = "STATUS")
  int status;
}
