package com.angelorobson.alternativescene.entities;

import com.angelorobson.alternativescene.enums.GenderEnum;
import com.angelorobson.alternativescene.enums.ProfileEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import static java.time.LocalDate.*;
import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "user_app")
public class UserApp implements Serializable {

    private static final long serialVersionUID = -6760436428804236349L;

    private Long id;
    private String googleAccountId;
    private String name;
    private String email;
    private String password;
    private ProfileEnum profile;
    private LocalDate dateBirth;
    private LocalDate registrationDate;
    private String imageUrl;
    private GenderEnum gender;
    private String phoneNumber;
    private Boolean activated;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGoogleAccountId(String googleAccountId) {
        this.googleAccountId = googleAccountId;
    }

    @Column(nullable = false)
    public String getGoogleAccountId() {
        return googleAccountId;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
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

    @JsonFormat(pattern = "yyyy/MM/dd")
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

    public String getImageUrl() {
        return imageUrl;
    }

    @Column(nullable = false)
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Enumerated(STRING)
    @Column(nullable = false)
    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(nullable = false)
    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    @PrePersist
    public void prePersist() {
        this.registrationDate = now();
        this.activated = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserApp userApp = (UserApp) o;
        return Objects.equals(id, userApp.id) &&
                Objects.equals(googleAccountId, userApp.googleAccountId) &&
                Objects.equals(name, userApp.name) &&
                Objects.equals(email, userApp.email) &&
                Objects.equals(password, userApp.password) &&
                profile == userApp.profile &&
                Objects.equals(dateBirth, userApp.dateBirth) &&
                Objects.equals(registrationDate, userApp.registrationDate) &&
                Objects.equals(imageUrl, userApp.imageUrl) &&
                gender == userApp.gender &&
                Objects.equals(phoneNumber, userApp.phoneNumber) &&
                Objects.equals(activated, userApp.activated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, googleAccountId, name, email, password, profile, dateBirth, registrationDate, imageUrl, gender, phoneNumber, activated);
    }

    @Override
    public String toString() {
        return "UserApp{" +
                "id=" + id +
                ", googleAccountId='" + googleAccountId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", profile=" + profile +
                ", dateBirth=" + dateBirth +
                ", registrationDate=" + registrationDate +
                ", imageUrl='" + imageUrl + '\'' +
                ", gender=" + gender +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", activated=" + activated +
                '}';
    }
}
