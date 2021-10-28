package uz.asbt.digid.common.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_NEW_CLIENTS")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Builder
public class Client implements Serializable {

    private static final long serialVersionUID = 3L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_generator")
    @SequenceGenerator(name = "client_generator", sequenceName = "SQ_TB_NEW_CLIENTS", allocationSize = 1)
    private long id;

    @ManyToOne
    @JoinColumn(name = "DEVICE_ID")
    private Device device;

    @Column(name = "COUNTRY_ID")
    private Long countryId;

    @Column(name = "REGION_ID")
    private Long regionId;

    @Column(name = "DISTRICT_ID")
    private Long districtId;

    @Column(name = "STREET")
    private String street;

    @Column(name = "HOME")
    private String home;

    @Column(name = "FLAT")
    private String flat;

    @Column(name = "BLOCK")
    private String block;

    @Column(name = "ADDITIONAL")
    private String additional;

    @Column(name = "MAC_ADDRESS")
    private String macAddress;

    @Column(name = "IP_ADDRESS")
    private String ipAddress;

    @Column(name = "OS")
    private String os;

    @Column(name = "APP_URL")
    private String appUrl;

    @Column(name = "APP_ID")
    private String appId;

    @Column(name = "CLIENT_PUB_KEY", unique = true)
    private String clientPubKey;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "IS_DELETED", columnDefinition = "int default 0")
    private int isDeleted;
}
