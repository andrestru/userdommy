package com.example.dummy.infrastructure.driver_adapters.repository;

import com.example.dummy.infrastructure.driver_adapters.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
}
