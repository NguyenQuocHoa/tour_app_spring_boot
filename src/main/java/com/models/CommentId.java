package com.models;

import java.io.Serializable;
import java.util.Objects;

public class CommentId implements Serializable {
    private int customer;
    private int post;

    @Override
    public int hashCode() {
        return Objects.hash(customer, post);
    }
}
