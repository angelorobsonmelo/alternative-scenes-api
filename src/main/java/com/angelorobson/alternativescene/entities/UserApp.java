package com.angelorobson.alternativescene.entities;

import com.angelorobson.alternativescene.enums.ProfileEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class UserApp implements Serializable {

    private static final long serialVersionUID = -5754246207015712518L;

    private Long id;
    private String name;
    private String email;
    private String password;
    private ProfileEnum profile;
    private LocalDate dateBirth;
    private LocalDate registrationDate;
    private String imageUrl;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public ProfileEnum getProfile() {
        return profile;
    }

    public void setProfile(ProfileEnum profile) {
        this.profile = profile;
    }

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(LocalDate dateBirth) {
        this.dateBirth = dateBirth;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  @Column(nullable = false)
  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  @PrePersist
    public void prePersist() {
        final LocalDate now = LocalDate.now();
        this.registrationDate = now;
    }

  @Override
  public String toString() {
    return "UserApp{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", email='" + email + '\'' +
      ", password='" + password + '\'' +
      ", profile=" + profile +
      ", dateBirth=" + dateBirth +
      ", registrationDate=" + registrationDate +
      ", imageUrl='" + imageUrl + '\'' +
      '}';
  }
}
