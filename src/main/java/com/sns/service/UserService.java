package com.sns.service;

import com.sns.exception.ErrorCode;
import com.sns.exception.SnsApplicationException;
import com.sns.model.Alarm;
import com.sns.model.Entity.UserEntity;
import com.sns.model.User;
import com.sns.repository.AlarmEntityRepository;
import com.sns.repository.UserEntityRepository;
import com.sns.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final AlarmEntityRepository alarmEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimesMs;

    public User loadUserByUserName(String userName){
        return userEntityRepository.findByUserName(userName).map(User::fromEntity).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName)));

    }

    @Transactional
    public User join(String userName , String password){

        // 회원가입 하려는 userName 으로 회원가입된 user가 있는지
     userEntityRepository.findByUserName(userName).ifPresent(it -> {
         throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME,String.format("%s is duplicated", userName));
     });

     // 회원가입 진행 = user를 등록
     UserEntity userEntity = userEntityRepository.save(UserEntity.of(userName,encoder.encode(password)));
  return User.fromEntity(userEntity);
}

public String login(String userName , String password){

        // 회원가입 여부 체크
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded",userName)));

        // 비밀번호 체크
    if(!encoder.matches(password,userEntity.getPassword())){
            throw new SnsApplicationException(ErrorCode.INVALID_PASSWORD);
        }

    String token = JwtTokenUtils.generateToken(userName,secretKey,expiredTimesMs);

        return token;
}

    public Page<Alarm> alarmList(Integer userId, Pageable pageable){
        return alarmEntityRepository.findAllByUserId(userId,pageable).map(Alarm::fromEntity);

    }
}
