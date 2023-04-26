package com.sns.fixture;

import com.sns.model.Entity.UserEntity;

public class UserEntityFixture {

    public static UserEntity get(String userName,String password){
        UserEntity result = new UserEntity();
        result.setId(1);
        result.setUserName(userName);
        result.setPassword(password);
        return result;
    }
}
