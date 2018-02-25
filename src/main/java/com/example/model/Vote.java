package com.example.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Timestamp;

@XmlRootElement
@Entity
public class Vote implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;
    private Timestamp date;
    @Enumerated(EnumType.STRING)
    private VoteType type;


    public Vote() {
    }

    public Vote(Timestamp date, VoteType type) {
        this.date = date;
        this.type = type;
    }

    public Long getVoteId() {
        return voteId;
    }

    public void setVoteId(Long voteId) {
        this.voteId = voteId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public VoteType getType() {
        return type;
    }

    public void setType(VoteType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "voteId=" + voteId +
                ", date=" + date +
                ", type=" + type +
                '}';
    }
}
