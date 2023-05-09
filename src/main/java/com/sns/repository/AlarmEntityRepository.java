package com.sns.repository;

import com.sns.model.Entity.AlarmEntity;
import com.sns.model.Entity.PostEntity;
import com.sns.model.Entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmEntityRepository extends JpaRepository<AlarmEntity, Integer> {

    Page<AlarmEntity> findAllByUser(UserEntity user, Pageable pageable);


}
