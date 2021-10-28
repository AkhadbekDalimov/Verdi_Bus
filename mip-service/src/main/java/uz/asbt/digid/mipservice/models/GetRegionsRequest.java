package uz.asbt.digid.mipservice.models;

/**
 * Created by KINS on 12.02.2020.
 */
public class GetRegionsRequest {
    private AuthInfo authInfo;

    public AuthInfo getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(AuthInfo authInfo) {
        this.authInfo = authInfo;
    }

    @Override
    public String toString() {
        return "GetRegionsRequest{" +
                "authInfo=" + authInfo +
                '}';
    }
}
