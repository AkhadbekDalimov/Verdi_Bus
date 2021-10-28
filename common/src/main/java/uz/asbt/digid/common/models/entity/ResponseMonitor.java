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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TB_NEW_MONITOR_RESPONSE", indexes = { @Index(name = "IDX_RES_DATE", columnList = "RESPONSE_DATE") })
public class ResponseMonitor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "monitor_response_generator")
    @SequenceGenerator(name = "monitor_response_generator", sequenceName = "SQ_TB_NEW_MONITOR_RESPONSE", allocationSize = 1)
    private long id;

    @Column(name = "GUID", nullable = false)
    private String guid;

    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "MESSAGE", nullable = false, length = 500)
    private String message;

    @Column(name = "RESPONSE_DATE")
    private LocalDateTime responseDate;
}
