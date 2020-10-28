package com.mydoctor.mydoctorbot.repository;

import com.mydoctor.mydoctorbot.model.SystemUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SystemUserRepository extends CrudRepository<SystemUser, Long> {
    Optional<SystemUser> findByTelegramId(Long telegramId);
}
