package uz.asbt.digid.common.models.rest;

import uz.asbt.digid.common.models.rest.PhysicalResponseBody;
import uz.asbt.digid.common.models.rest.PhysicalResponseHeader;
import uz.asbt.digid.common.models.rest.PhysicalResponseRequest;
import uz.asbt.digid.common.models.rest.PhysicalResponseResult;

public class PhysicalPhotoResponse {
    private PhysicalResponseResult result;

    private PhysicalResponseHeader header;

    private PhysicalResponseRequest request;

    private PhysicalPhotoResponseBody response;

    public PhysicalPhotoResponse() {
        super();
    }

    public PhysicalPhotoResponse(PhysicalResponseResult result, PhysicalResponseHeader header, PhysicalResponseRequest request,
                                 PhysicalPhotoResponseBody response) {
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

    public PhysicalPhotoResponseBody getResponse() {
        return response;
    }

    public void setResponse(PhysicalPhotoResponseBody response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "PhysicalResponse [result=" + result + ", header=" + header + ", request=" + request + ", response="
                + response + "]";
    }
}
