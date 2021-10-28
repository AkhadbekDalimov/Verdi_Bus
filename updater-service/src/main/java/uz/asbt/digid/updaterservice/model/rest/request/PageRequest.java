package uz.asbt.digid.updaterservice.model.rest.request;

/**
 * Created by User on 15.01.2020.
 */
public class PageRequest extends BaseAuth{
    private int page;
    private int size;

    public PageRequest(String ipAddress, String macAddress, String serialNumber,
                       String sign, int page, int size) {
        super(ipAddress, macAddress, serialNumber, sign);
        this.page = page;
        this.size = size;
    }

    public PageRequest() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "PageRequest{" +
                "page=" + page +
                ", size=" + size +
                '}';
    }
}
