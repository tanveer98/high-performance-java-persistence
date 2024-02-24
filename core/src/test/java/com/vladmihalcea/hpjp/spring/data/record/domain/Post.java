package com.vladmihalcea.hpjp.spring.data.record.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vlad Mihalcea
 */
@Entity(name = "Post")
@Table(name = "post")
public class Post {

    @Id
    private Long id;

    private String title;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostComment> comments = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public Post setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Post setTitle(String title) {
        this.title = title;
        return this;
    }

    public List<PostComment> getComments() {
        return comments;
    }

    public Post addComment(PostComment comment) {
        comments.add(comment);
        comment.setPost(this);
        return this;
    }

    public PostRecord toRecord() {
        return new PostRecord(
            id,
            title,
            comments.stream().map(comment ->
                new PostCommentRecord(
                    comment.getId(),
                    comment.getReview()
                )
            ).toList()
        );
    }
}
