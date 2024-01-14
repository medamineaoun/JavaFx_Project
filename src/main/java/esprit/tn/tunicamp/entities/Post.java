package esprit.tn.tunicamp.entities;

import java.sql.Timestamp;
public class Post {
    private Long id;
    private Camper camper;
    private String content;
    private Timestamp createdAt;

    public Post(Long id, Camper camper, String content, Timestamp createdAt) {
        this.id = id;
        this.camper = camper;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Camper getCamper() {
        return camper;
    }

    public void setCamper(Camper camper) {
        this.camper = camper;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}