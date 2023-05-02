package com.sns.controller.response;

import com.sns.model.Post;
import com.sns.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;


@AllArgsConstructor
@Getter
public class PostResponse {
    private Integer id;

    private String title;

    private String body;

    private UserResponse user;

    private Timestamp registerAt;

    private Timestamp updatedAt;

    private Timestamp deleted_At;

    public static PostResponse fromPost(Post post){
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getBody(),
                UserResponse.fromUser(post.getUser()),
                post.getRegisterAt(),
                post.getUpdatedAt(),
                post.getDeleted_At()
        );
    }
}
