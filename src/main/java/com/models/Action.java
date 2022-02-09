package com.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "action")
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    private String text;

    @OneToMany(mappedBy = "action")
    @JsonIgnore
    private Set<CommentDetail> commentDetails;

    public Action() {
    }

    public Action(Long id, String code, String text) {
        this.id = id;
        this.code = code;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        Action action = (Action) o;
        return Objects.equals(id, action.id) && Objects.equals(code, action.code) && Objects.equals(text, action.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, text);
    }
}
