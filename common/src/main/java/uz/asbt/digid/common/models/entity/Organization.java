package uz.asbt.digid.common.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_NEW_ORGANIZATIONS")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@ToString(exclude = {"devices"})
@EqualsAndHashCode(exclude = {"devices"})
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "org_generator")
    @SequenceGenerator(name="org_generator", sequenceName = "SQ_TB_NEW_ORGANIZATIONS", allocationSize = 1)
    private long id;

    @Column(name = "CRM_ORG_ID", unique = true, nullable = false)
    private String orgCrmId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @NotBlank
    @Column(name = "NAME", nullable = false)
    private String name;

    @OneToMany
    private Collection<Device> devices;

    @Column(name = "PINPP")
    private String pinpp;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
}
