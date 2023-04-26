package com.sns.controller.response;

import com.sns.model.User;
import com.sns.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginResponse {

    private String token;
}
