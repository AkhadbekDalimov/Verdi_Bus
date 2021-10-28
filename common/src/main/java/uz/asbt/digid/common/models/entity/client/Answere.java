package uz.asbt.digid.common.models.entity.client;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class Answere {

    @JsonProperty("AnswereId")
    @JsonAlias("answereId")
    private int answereId;

    @JsonProperty("AnswereComment")
    @JsonAlias("answereComment")
    private String answereComment;

    public Answere() {
    }

    public Answere(int answereId, String answereComment) {
        this.answereId = answereId;
        this.answereComment = answereComment;
    }

    public int getAnswereId() {
        return answereId;
    }

    public void setAnswereId(int answereId) {
        this.answereId = answereId;
    }

    public String getAnswereComment() {
        return answereComment;
    }

    public void setAnswereComment(String answereComment) {
        this.answereComment = answereComment;
    }

    @Override
    public String toString() {
        return "Answere{" +
                "answereId=" + answereId +
                ", answereComment='" + answereComment + '\'' +
                '}';
    }
}
