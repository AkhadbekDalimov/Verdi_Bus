package uz.asbt.digid.updaterservice.model.rest.request;

/**
 * Created by User on 15.01.2020.
 */
public class SaveUpdateRequest extends BaseAuth{
    private String applicationName;
    private String filePath;
    private String version;

    public SaveUpdateRequest(String ipAddress, String macAddress, String serialNumber, String sign,
                             String applicationName, String filePath, String version) {
        super(ipAddress, macAddress, serialNumber, sign);
        this.applicationName = applicationName;
        this.filePath = filePath;
        this.version = version;
    }

    public SaveUpdateRequest() {
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "SaveUpdateRequest{" +
                "applicationName='" + applicationName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
