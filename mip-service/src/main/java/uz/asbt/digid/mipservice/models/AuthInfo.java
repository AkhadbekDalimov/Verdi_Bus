package uz.asbt.digid.mipservice.models;

/**
 * Created by KINS on 11.02.2020.
 */
public class AuthInfo {
    private String userSessionId;
    private String wsid;
    private String leid;

    public String getUserSessionId() {
        return userSessionId;
    }

    public void setUserSessionId(String userSessionId) {
        this.userSessionId = userSessionId;
    }

    public String getWsid() {
        return wsid;
    }

    public void setWsid(String wsid) {
        this.wsid = wsid;
    }

    public String getLeid() {
        return leid;
    }

    public void setLeid(String leid) {
        this.leid = leid;
    }

    @Override
    public String toString() {
        return "AuthInfo{" +
                "userSessionId='" + userSessionId + '\'' +
                ", wsid='" + wsid + '\'' +
                ", leid='" + leid + '\'' +
                '}';
    }
}
