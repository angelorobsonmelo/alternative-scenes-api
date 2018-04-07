package com.angelorobson.alternativescene.builders;

import com.angelorobson.alternativescene.entities.UserApp;
import com.angelorobson.alternativescene.enums.ProfileEnum;

import java.time.LocalDate;

import static com.angelorobson.alternativescene.enums.ProfileEnum.*;
import static com.angelorobson.alternativescene.utils.PasswordUtils.*;
import static java.time.LocalDate.*;
import static java.time.Month.*;

public class UserAppBuilder {

  private Long id;
  private String name;
  private String email;
  private String password;
  private ProfileEnum profile;
  private LocalDate dateBirth;
  private LocalDate registrationDate;
  private String imageUrl;

  private UserApp userApp;

  public UserAppBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public UserAppBuilder name(String name) {
    this.name = name;
    return this;
  }

  public UserAppBuilder email(String email) {
    this.email = email;
    return this;
  }

  public UserAppBuilder password(String password) {
    this.password = password;
    return this;
  }

  public UserAppBuilder profile(ProfileEnum profile) {
    this.profile = profile;
    return this;
  }

  public UserAppBuilder dateBirth(LocalDate dateBirth) {
    this.dateBirth = dateBirth;
    return this;
  }

  public UserAppBuilder registrationDate(LocalDate registrationDate) {
    this.registrationDate = registrationDate;
    return this;
  }

  public UserAppBuilder imageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }

  public static UserAppBuilder oneUserAppAdmin() {
    UserAppBuilder builder = new UserAppBuilder();
    builder.userApp.setId(1L);
    builder.userApp.setName("José");
    builder.userApp.setEmail("jose@gmail.com");
    builder.userApp.setPassword(generateBCrypt("123"));
    builder.userApp.setImageUrl("https://image_1");
    builder.userApp.setProfile(ROLE_ADMIN);
    builder.userApp.setDateBirth(of(2014, JANUARY, 1));
    builder.userApp.setRegistrationDate(now());

    return builder;
  }

  public UserApp build() {
    return userApp;
  }

}
