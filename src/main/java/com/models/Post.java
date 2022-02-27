package com.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
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
    private Boolean isActive;
    private Long creator;
    private Date created;
    @OneToMany(mappedBy = "post")
    @JsonIgnore
    private Set<Comment> comments;

    public Post() {
    }

    public Post(long id, String code, String note, String content, Boolean isActive, Long creator, Date created) {
        this.id = id;
        this.code = code;
        this.note = note;
        this.content = content;
        this.isActive = isActive;
        this.creator = creator;
        this.created = created;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id && Objects.equals(code, post.code) && Objects.equals(note, post.note) && Objects.equals(content, post.content) && Objects.equals(isActive, post.isActive) && Objects.equals(creator, post.creator) && Objects.equals(created, post.created) && Objects.equals(comments, post.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, note, content, isActive, creator, created, comments);
    }
}
