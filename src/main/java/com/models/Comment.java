package com.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "comment")
@IdClass(CommentId.class)
public class Comment {
    @Id
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @Id
    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    private String action;
    private String text;

    public Comment() {
    }

    public Comment(String action, String text) {
        this.action = action;
        this.text = text;
    }

    public Comment(Customer customer, Post post, String action, String text) {
        this.customer = customer;
        this.post = post;
        this.action = action;
        this.text = text;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(customer, comment.customer) && Objects.equals(post, comment.post) && Objects.equals(action, comment.action) && Objects.equals(text, comment.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, post, action, text);
    }
}
