package uz.asbt.digid.common.models.rest;

public class GridRequest {
    private Integer page = 0;
    private Integer size = 10;

    public GridRequest() {
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
        return "GridRequest{" +
                "page=" + page +
                ", size=" + size +
                '}';
    }
}
