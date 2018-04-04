package com.angelorobson.alternativescene.services.impl;

import com.angelorobson.alternativescene.entities.UserApp;
import com.angelorobson.alternativescene.repositories.UserAppRepository;
import com.angelorobson.alternativescene.services.UserAppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAppAppServiceImpl implements UserAppService {

    private static final Logger log = LoggerFactory.getLogger(UserAppAppServiceImpl.class);

    @Autowired
    private UserAppRepository userAppRepository;

    @Override
    public UserApp persist(UserApp userApp) {
        log.info("Persisting a UserApp {}", userApp);
        return userAppRepository.save(userApp);
    }

    @Override
    public Optional<UserApp> findByEmail(String email) {
        log.info("Finding a UserApp by Email: {}", email);
        return Optional.ofNullable(userAppRepository.findByEmail(email));
    }

    @Override
    public Optional<UserApp> findById(Long id) {
        log.info("Finding a UserApp by ID: {}", id);
        return Optional.ofNullable(userAppRepository.findOne(id));
    }
}
