package com.sns.repository;

import com.sns.model.Entity.PostEntity;
import com.sns.model.Entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostEntityRepository extends JpaRepository<PostEntity, Integer> {

    Page<PostEntity> findAllByUser(UserEntity entity, Pageable pageable);


}
