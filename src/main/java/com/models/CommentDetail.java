package com.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "comment_detail")
public class CommentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Comment comment;


    @ManyToOne
    @JoinColumn(name = "action_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Action action;
}
