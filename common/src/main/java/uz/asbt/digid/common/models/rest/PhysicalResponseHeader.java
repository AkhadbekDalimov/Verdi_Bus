package uz.asbt.digid.common.models.rest;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PhysicalResponseHeader {

    @JsonProperty("query_id")
    @JsonAlias("queryId")
    private String queryId;

    private String inquire;

    private String respond;

    public PhysicalResponseHeader() {
        super();
    }

    public PhysicalResponseHeader(String queryId, String inquire, String respond) {
        super();
        this.queryId = queryId;
        this.inquire = inquire;
        this.respond = respond;
    }


    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public String getInquire() {
        return inquire;
    }

    public void setInquire(String inquire) {
        this.inquire = inquire;
    }

    public String getRespond() {
        return respond;
    }

    public void setRespond(String respond) {
        this.respond = respond;
    }

    @Override
    public String toString() {
        return "PhysicalResponseHeader [queryId=" + queryId + ", inquire=" + inquire + ", respond=" + respond + "]";
    }


}
