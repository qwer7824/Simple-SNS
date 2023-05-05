package com.sns.service;

import com.sns.exception.ErrorCode;
import com.sns.exception.SnsApplicationException;
import com.sns.model.Entity.LikeEntity;
import com.sns.model.Entity.PostEntity;
import com.sns.model.Entity.UserEntity;
import com.sns.model.Post;
import com.sns.repository.LikeEntityRepository;
import com.sns.repository.PostEntityRepository;
import com.sns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final LikeEntityRepository likeEntityRepository;

    @Transactional
    public void create(String title, String body, String userName) {

        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName)));
        postEntityRepository.save(PostEntity.of(title, body, userEntity));
    }

    @Transactional
    public Post modify(String title, String body, String userName, Integer postId) {
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName)));

        PostEntity postEntity = postEntityRepository.findById(postId).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s not founded", postId)));

        if (postEntity.getUser() != userEntity) {
            throw new SnsApplicationException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", userName, postId));
        }
            postEntity.setTitle(title);
            postEntity.setBody(body);

            return Post.fromEntity(postEntityRepository.saveAndFlush(postEntity));
        }

        @Transactional
        public void delete(String userName, Integer postId){
            UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() ->
                    new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName)));

            PostEntity postEntity = postEntityRepository.findById(postId).orElseThrow(() ->
                    new SnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s not founded", postId)));
            if (postEntity.getUser() != userEntity) {
                throw new SnsApplicationException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", userName, postId));
            }
            postEntityRepository.delete(postEntity);
    }

    public Page<Post> list(Pageable pageable){
        return postEntityRepository.findAll(pageable).map(Post::fromEntity);
        }

    public Page<Post> my(String userName, Pageable pageable){

        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName)));

        return postEntityRepository.findAllByUser(userEntity, pageable).map(Post::fromEntity);
    }

    @Transactional
    public void like(Integer postId, String userName){
        PostEntity postEntity = postEntityRepository.findById(postId).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s not founded", postId)));
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName)));

        likeEntityRepository.findByUserAndPost(userEntity,postEntity).ifPresent(it ->{
            throw new SnsApplicationException(ErrorCode.ALREADY_LIKED, String.format("userName %s already like post %d", userName,postId));
        });

        likeEntityRepository.save(LikeEntity.of(userEntity,postEntity));
    }

    @Transactional
    public int likeCount(Integer postId){
        PostEntity postEntity = postEntityRepository.findById(postId).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s not founded", postId)));

        //List<LikeEntity> likeEntities = likeEntityRepository.findByPost(postEntity);
        //return likeEntities.size();

        return likeEntityRepository.countByPost(postEntity);
    }

}

