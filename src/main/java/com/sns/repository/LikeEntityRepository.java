package com.sns.repository;

import com.sns.model.Entity.LikeEntity;
import com.sns.model.Entity.PostEntity;
import com.sns.model.Entity.UserEntity;
import com.sns.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeEntityRepository extends JpaRepository<LikeEntity, Integer> {
    Optional<LikeEntity> findByUserAndPost(UserEntity user, PostEntity post);

    //select count(*) from "like" where post_id
    @Query(value = "SELECT COUNT(*) FROM LikeEntity entity WHERE entity.post = :post")
    Integer countByPost(@Param("post") PostEntity post);


    List<LikeEntity> findByPost(PostEntity post);

}
