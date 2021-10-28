package uz.asbt.digid.updaterservice.model;

import uz.asbt.digid.updaterservice.model.audit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by User on 15.01.2020.
 */
@Entity
@Table(name = "tb_digid_updates")
public class Update extends DateAudit implements Serializable{

    private static final long serialVersionUID = 1234L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "updates_sequence")
    @SequenceGenerator(name = "updates_sequence", sequenceName = "tb_digid_updates_seq")
    private Long id;

    @NotNull
    @Column(name = "os_file_path")
    private String osFilePath;

    @Column(name = "application_name")
    private String applicationName;

    @NotNull
    @Column(name = "version")
    private String version;

    @NotNull
    @Column(name = "hash")
    private String hash;

    @NotNull
    @Column(name = "file_size")
    private Long fileSizeInBytes;


    public Update(@NotNull String osFilePath, String applicationName,
                  @NotNull String version,
                  @NotNull String hash, @NotNull Long fileSizeInBytes) {
        this.osFilePath = osFilePath;
        this.applicationName = applicationName;
        this.version = version;
        this.hash = hash;
        this.fileSizeInBytes = fileSizeInBytes;
    }

    public Update() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOsFilePath() {
        return osFilePath;
    }

    public void setOsFilePath(String osFilePath) {
        this.osFilePath = osFilePath;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Long getFileSizeInBytes() {
        return fileSizeInBytes;
    }

    public void setFileSizeInBytes(Long fileSizeInBytes) {
        this.fileSizeInBytes = fileSizeInBytes;
    }

    @Override
    public String toString() {
        return "Update{" +
                "id=" + id +
                ", osFilePath='" + osFilePath + '\'' +
                ", applicationName='" + applicationName + '\'' +
                ", version='" + version + '\'' +
                ", hash='" + hash + '\'' +
                ", fileSizeInBytes=" + fileSizeInBytes +
                '}';
    }
}
