package com.example.dummy.infrastructure.driver_adapters.adapters;

import com.example.dummy.domain.model.SaveUser;
import com.example.dummy.domain.model.repository.LoginRepository;
import com.example.dummy.infrastructure.driver_adapters.entity.UserEntity;
import com.example.dummy.infrastructure.driver_adapters.repository.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserAdapter implements LoginRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public SaveUser save(SaveUser user) {
        UserEntity entity = new UserEntity().builder()
                .username(user.getUsername())
                .loginTime(user.getLoginTime())
                .accessToken(user.getAccessToken())
                .refreshToken(user.getRefreshToken())
                .build();

        userJpaRepository.save(entity);
        return user;
    }
}
