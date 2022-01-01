package com.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "post")
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String code;
    private String note;
    private String content;
    @OneToMany(mappedBy = "post")
    private Set<Comment> comments;

    public Post() {
    }

    public Post(long id, String code, String note, String content) {
        this.id = id;
        this.code = code;
        this.note = note;
        this.content = content;
    }

    public Post(long id, String code, String note, String content, Set<Comment> comments) {
        this.id = id;
        this.code = code;
        this.note = note;
        this.content = content;
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", note='" + note + '\'' +
                ", content='" + content + '\'' +
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
}
