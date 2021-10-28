package uz.asbt.digid.updaterservice.model.rest.request;

/**
 * Created by User on 17.01.2020.
 */
public class DeleteUpdateRequest extends BaseAuth{
    private Long id;

    public DeleteUpdateRequest(String ipAddress, String macAddress,
                               String serialNumber, String sign, Long id) {
        super(ipAddress, macAddress, serialNumber, sign);
        this.id = id;
    }

    public DeleteUpdateRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DeleteUpdateRequest{" +
                "id=" + id +
                '}';
    }
}
