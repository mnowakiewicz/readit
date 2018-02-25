package com.example.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@XmlRootElement
@Entity
public class Discovery implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discoveryId;
    private String name;
    @Column(length = 1024)
    private String description;
    private String shortDescription;
    private String url;
    private Timestamp date;
    private int upVote;
    private int downVote;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idUser", referencedColumnName = "userId")
    private User user;

    @OneToMany
    @JoinColumn(name = "idDiscovery", referencedColumnName = "discoveryId")
    private List<Vote> votes;

    public Discovery() {
    }

    public Discovery(String name, String description, String shortDescription, String url, Timestamp date, int upVote, int downVote) {
        this.name = name;
        this.description = description;
        this.shortDescription = shortDescription;
        this.url = url;
        //this.date = date;
        this.upVote = upVote;
        this.downVote = downVote;
    }

    public Long getDiscoveryId() {
        return discoveryId;
    }

    public void setDiscoveryId(Long discoveryId) {
        this.discoveryId = discoveryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getUpVote() {
        return upVote;
    }

    public void setUpVote(int upVote) {
        this.upVote = upVote;
    }

    public int getDownVote() {
        return downVote;
    }

    public void setDownVote(int downVote) {
        this.downVote = downVote;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}