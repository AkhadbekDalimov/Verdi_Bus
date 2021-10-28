package uz.asbt.digid.digidservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "SP_DOCTYPE")
public class DocType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "SP_ID")
    private long id;

    @Column(name = "SP_UZB")
    private String uzb;

    @Column(name = "SP_LAT")
    private String lat;

    @Column(name = "SP_RUS")
    private String rus;

    @Column(name = "SP_ENG")
    private String eng;

    @Column(name = "SP_ICAOCODE")
    private String icaoCode;

    @Column(name = "SP_BANKCODE")
    private long bankCode;

    @Column(name = "SP_ACTIVE")
    private int active;

    @Column(name = "SP_DATEENTER")
    private Date date;
}
