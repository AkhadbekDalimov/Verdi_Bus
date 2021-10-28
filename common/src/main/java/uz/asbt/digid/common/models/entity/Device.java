package uz.asbt.digid.common.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "TB_NEW_DEVICES")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@ToString(exclude = {"clients"})
@EqualsAndHashCode(exclude = {"clients"})
@Builder
public class Device implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "device_generator")
    @SequenceGenerator(name="device_generator", sequenceName = "SQ_TB_NEW_DEVICES", allocationSize = 1)
    private long id;

    @Column(name = "ORG_CRM_ID")
    private String orgCrmId;

    @Column(name = "DEVICE_CRM_ID", unique = true, nullable = false)
    private String deviceCrmId;

    @Column(name = "DEVICE_TYPE_ID", nullable = false)
    private long deviceTypeId;

    @NotBlank
    @Column(name = "SERIAL_NUMBER", unique = true, nullable = false)
    private String serialNumber;

    @Column(name = "STATUS", columnDefinition = "int default 0")
    private long status;

    @Column(name = "TEST")
    private boolean test;

    @ManyToOne
    @JoinColumn(name = "ORG_ID")
    private Organization organization;

    @OneToMany(mappedBy = "device")
    @JsonIgnore
    private Collection<Client> clients;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;


    @Column(name = "LIVENESS_CHECK", columnDefinition = "int default 0")
    private int livenessCheck;

    @Column(name = "SIMILARITY_CHECK", columnDefinition = "int default 0")
    private int similarityCheck;

}
