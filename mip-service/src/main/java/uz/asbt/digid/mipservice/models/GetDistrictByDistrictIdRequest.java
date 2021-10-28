package uz.asbt.digid.mipservice.models;

/**
 * Created by KINS on 11.02.2020.
 */
public class GetDistrictByDistrictIdRequest {
    private Long id;
    private AuthInfo authInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuthInfo getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(AuthInfo authInfo) {
        this.authInfo = authInfo;
    }

    @Override
    public String toString() {
        return "GetDistrictByDistrictIdRequest{" +
                "id='" + id + '\'' +
                ", authInfo=" + authInfo +
                '}';
    }
}
