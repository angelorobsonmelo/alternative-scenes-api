package com.angelorobson.alternativescene.dtos;


import java.time.LocalDate;
import java.util.List;

public class EventDto {

    private Long id;
    private String title;
    private String description;
    private String photoUrl;
    private String locality;
    private String city;
    private String state;
    private List<EventDateDto> eventDates;
    private List<MusicalGenreDto> musicalGenres;
    private String category;
    private Boolean status;
    private LocalDate registrationDate;
    private UserAppDto userApp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<EventDateDto> getEventDates() {
        return eventDates;
    }

    public void setEventDates(List<EventDateDto> eventDates) {
        this.eventDates = eventDates;
    }

    public List<MusicalGenreDto> getMusicalGenres() {
        return musicalGenres;
    }

    public void setMusicalGenres(List<MusicalGenreDto> musicalGenres) {
        this.musicalGenres = musicalGenres;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public UserAppDto getUserApp() {
        return userApp;
    }

    public void setUserApp(UserAppDto userApp) {
        this.userApp = userApp;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
}
