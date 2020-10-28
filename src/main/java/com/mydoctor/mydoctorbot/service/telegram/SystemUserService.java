package com.mydoctor.mydoctorbot.service.telegram;

import com.mydoctor.mydoctorbot.model.SystemUser;
import com.mydoctor.mydoctorbot.repository.SystemUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SystemUserService {

    private final SystemUserRepository systemUserRepository;

    public SystemUser auth(Long telegramId) {
        Optional<SystemUser> optional = systemUserRepository.findByTelegramId(telegramId);

        if (optional.isEmpty()) {
            SystemUser systemUser = new SystemUser();
            systemUser.setTelegramId(telegramId);

            SystemUser created = systemUserRepository.save(systemUser);

            log.info("New system user {} saved", created);

            return created;
        }

        return optional.get();
    }

    public SystemUser update(SystemUser systemUser) {
        return systemUserRepository.save(systemUser);
    }
}
