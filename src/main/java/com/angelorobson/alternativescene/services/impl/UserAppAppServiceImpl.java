package com.angelorobson.alternativescene.services.impl;

import com.angelorobson.alternativescene.converters.Converters;
import com.angelorobson.alternativescene.dtos.UserAppDto;
import com.angelorobson.alternativescene.entities.UserApp;
import com.angelorobson.alternativescene.repositories.UserAppRepository;
import com.angelorobson.alternativescene.services.UserAppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.angelorobson.alternativescene.converters.Converters.*;

@Service
public class UserAppAppServiceImpl implements UserAppService {

  private static final Logger log = LoggerFactory.getLogger(UserAppAppServiceImpl.class);

  @Autowired
  private UserAppRepository userAppRepository;

  @Override
  public UserAppDto persist(UserApp userApp) {
    log.info("Persisting a UserApp {}", userApp);
    UserApp userAppReturned = userAppRepository.save(userApp);
    return convertUserAppEntityToDto(userAppReturned);
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

  @Override
  public Page<UserAppDto> findAll(PageRequest pageRequest) {
    log.info("Get all users {}", pageRequest);
    Page<UserApp> userAppPage = userAppRepository.findAll(pageRequest);

    return userAppPage.map(Converters::convertUserAppEntityToDto);
  }

  @Override
  public UserAppDto edit(UserApp userApp) {
    log.info("Editing a UserApp {}", userApp);
    UserApp userAppReturned = userAppRepository.save(userApp);
    return convertUserAppEntityToDto(userAppReturned);
  }

    @Override
    public void remove(Long id) {
        log.info("Removing a UserApp with {}", id);
        userAppRepository.delete(id);
    }

}
