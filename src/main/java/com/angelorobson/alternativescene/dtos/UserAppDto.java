package com.angelorobson.alternativescene.dtos;

import java.time.LocalDate;

public class UserAppDto {

  private Long id;
  private String name;
  private String imageUrl;
  private String email;
  private LocalDate registrationDate;
  private String googleAccountId;

    public UserAppDto() {
    }

    public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getGoogleAccountId() {
    return googleAccountId;
  }

  public void setGoogleAccountId(String googleAccountId) {
    this.googleAccountId = googleAccountId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public LocalDate getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(LocalDate registrationDate) {
    this.registrationDate = registrationDate;
  }

  @Override
  public String toString() {
    return "UserAppDto{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", imageUrl='" + imageUrl + '\'' +
      ", email='" + email + '\'' +
      '}';
  }
}
