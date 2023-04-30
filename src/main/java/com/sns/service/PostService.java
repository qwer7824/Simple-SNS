package com.sns.service;

import com.sns.exception.ErrorCode;
import com.sns.exception.SnsApplicationException;
import com.sns.model.Entity.PostEntity;
import com.sns.model.Entity.UserEntity;
import com.sns.repository.PostEntityRepository;
import com.sns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final UserEntityRepository userEntityRepository;

    @Transactional
    public void create(String title, String body, String userName){

      UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() ->
              new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName)));
      postEntityRepository.save(PostEntity.of(title,body,userEntity));
    }
}
