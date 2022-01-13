package com.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "post")
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String code;
    @Column(length = 1000)
    private String note;
    private String content;
    private Long creator;
    private Date created;
    @OneToMany(mappedBy = "post")
    @JsonIgnore
    private Set<Comment> comments;

    public Post() {
    }

    public Post(long id, String code, String note, String content) {
        this.id = id;
        this.code = code;
        this.note = note;
        this.content = content;
    }

    public Post(long id, String code, String note, String content, Long creator, Date created, Set<Comment> comments) {
        this.id = id;
        this.code = code;
        this.note = note;
        this.content = content;
        this.creator = creator;
        this.created = created;
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", note='" + note + '\'' +
                ", content='" + content + '\'' +
                ", creator=" + creator +
                ", created=" + created +
                ", comments=" + comments +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
