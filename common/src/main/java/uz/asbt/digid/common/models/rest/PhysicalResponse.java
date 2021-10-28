package uz.asbt.digid.common.models.rest;

public class PhysicalResponse {
    private PhysicalResponseResult result;

    private PhysicalResponseHeader header;

    private PhysicalResponseRequest request;

    private PhysicalResponseBody response;

    public PhysicalResponse() {
        super();
    }

    public PhysicalResponse(PhysicalResponseResult result, PhysicalResponseHeader header, PhysicalResponseRequest request,
                            PhysicalResponseBody response) {
        super();
        this.result = result;
        this.header = header;
        this.request = request;
        this.response = response;
    }

    public PhysicalResponseResult getResult() {
        return result;
    }

    public void setResult(PhysicalResponseResult result) {
        this.result = result;
    }

    public PhysicalResponseHeader getHeader() {
        return header;
    }

    public void setHeader(PhysicalResponseHeader header) {
        this.header = header;
    }

    public PhysicalResponseRequest getRequest() {
        return request;
    }

    public void setRequest(PhysicalResponseRequest request) {
        this.request = request;
    }

    public PhysicalResponseBody getResponse() {
        return response;
    }

    public void setResponse(PhysicalResponseBody response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "PhysicalResponse [result=" + result + ", header=" + header + ", request=" + request + ", response="
                + response + "]";
    }
}
