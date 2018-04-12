package com.angelorobson.alternativescene.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Optional;

public class UserAppEditDto {

  private Optional<Long> id = Optional.empty();
  private String name;
  private String password;
  private LocalDate dateBirth;
  private String imageUrl;


  public Optional<Long> getId() {
    return id;
  }

  public void setId(Optional<Long> id) {
    this.id = id;
  }

  @NotEmpty(message = "Name can not be empty.")
  @Length(min = 3, max = 200, message = "Name must contain between 3 and 200 characters.")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @NotNull(message = "DateBirth can not be empty.")
  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  public LocalDate getDateBirth() {
    return dateBirth;
  }

  public void setDateBirth(LocalDate dateBirth) {
    this.dateBirth = dateBirth;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  @Override
  public String toString() {
    return "UserAppEditDto {" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", password='" + password + '\'' +
      ", dateBirth=" + dateBirth +
      '}';
  }
}
