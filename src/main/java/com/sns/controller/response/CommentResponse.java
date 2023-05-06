package com.sns.controller.response;

import com.sns.model.Comment;
import com.sns.model.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;


@AllArgsConstructor
@Getter
public class CommentResponse {
    private Integer id;
    private String comment;
    private String userName;
    private Integer postId;
    private Timestamp registerAt;

    private Timestamp updatedAt;

    private Timestamp deleted_At;

    public static CommentResponse fromComment(Comment comment){
        return new CommentResponse(
                comment.getId(),
                comment.getComment(),
                comment.getUserName(),
                comment.getPostId(),
                comment.getRegisterAt(),
                comment.getUpdatedAt(),
                comment.getDeleted_At()
        );
    }
}
