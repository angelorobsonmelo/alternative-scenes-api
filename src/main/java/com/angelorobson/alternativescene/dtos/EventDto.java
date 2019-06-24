package com.angelorobson.alternativescene.dtos;


import java.time.LocalDate;
import java.util.List;

public class EventDto {

    private Long id;
    private String title;
    private String description;
    private String photoUrl;
    private LocalityDto locality;
    private List<EventDateDto> eventDates;
    private List<MusicalGenreDto> musicalGenres;
    private CategoryDto category;
    private Boolean status;
    private LocalDate registrationDate;
    private UserAppDto userApp;
    private String eventDate;
    private String eventLocation;

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

    public LocalityDto getLocality() {
        return locality;
    }

    public void setLocality(LocalityDto locality) {
        this.locality = locality;
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

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
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

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public void setEventLocation() {
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }
}
