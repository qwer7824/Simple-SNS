package com.sns.fixture;


import com.sns.model.Entity.PostEntity;
import com.sns.model.Entity.UserEntity;

public class PostEntityFixture {

    public static PostEntity get(String userName, Integer postId,Integer userId){ // PostEntity 를 반환해주는 메소드
        UserEntity user = new UserEntity();
        user.setId(1);
        user.setUserName(userName);

        PostEntity result = new PostEntity();
        result.setUser(user);
        result.setId(postId);

        return result;
    }
}
