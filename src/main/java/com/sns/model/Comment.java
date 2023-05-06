package com.sns.model;

import com.sns.model.Entity.CommentEntity;
import com.sns.model.Entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Comment {
    private Integer id;
    private String comment;
    private String userName;
    private Integer postId;
    private Timestamp registerAt;
    private Timestamp updatedAt;
    private Timestamp deleted_At;

    public static Comment fromEntity(CommentEntity entity){ //Entity -> DTO
        return new Comment(
                entity.getId(),
                entity.getComment(),
                entity.getUser().getUserName(),
                entity.getPost().getId(),
                entity.getRegisterAt(),
                entity.getUpdatedAt(),
                entity.getDeleted_At()
        );
    }
}
