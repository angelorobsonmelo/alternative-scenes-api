package com.angelorobson.alternativescene.services;

import com.angelorobson.alternativescene.dtos.UserAppDto;
import com.angelorobson.alternativescene.entities.UserApp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface UserAppService {

  /**
   * A userApp persists in the database.
   *
   * @param userApp
   * @return UserApp
   */
  UserApp persist(UserApp userApp);

  /**
   * Search and return a given user an email.
   *
   * @param email
   * @return Optional<UserApp>
   */
  Optional<UserApp> findByEmail(String email);

  /**
   * Search and return a user by ID.
   *
   * @param id
   * @return Optional<UserApp>
   */
  Optional<UserApp> findById(Long id);

  /**
   * Search all users
   *
   * @param pageRequest
   * @return Page<UserApp>
   */
  Page<UserAppDto> findAll(PageRequest pageRequest);
}
