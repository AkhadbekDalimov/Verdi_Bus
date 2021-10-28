package uz.asbt.digid.common.models.entity.auth;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Table(name = "SERVICE_AUTH")
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_generator")
    @SequenceGenerator(name = "auth_generator", sequenceName = "SQ_TB_SERVICE_AUTH", allocationSize = 1)
    private Long id;

    @NotBlank
    @Column(name = "LOGIN", unique = true, nullable = false)
    private String login;

    @NotBlank
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "STATUS", nullable = false)
    private int status;

    @Column(name = "CREATED_DATE", nullable = false)
    private Timestamp createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Auth{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", createdDate=" + createdDate +
                '}';
    }
}
