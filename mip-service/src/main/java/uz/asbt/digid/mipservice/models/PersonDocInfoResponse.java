package uz.asbt.digid.mipservice.models;

/**
 * Created by KINS on 27.02.2020.
 */
public class PersonDocInfoResponse {
    protected String result;
    protected DataCEPResponse data;
    protected String comments;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public DataCEPResponse getData() {
        return data;
    }

    public void setData(DataCEPResponse data) {
        this.data = data;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "PersonDocInfoResponse{" +
                "result='" + result + '\'' +
                ", data=" + data +
                ", comments='" + comments + '\'' +
                '}';
    }
}
