package com.angelorobson.alternativescene.dtos;

import java.util.List;

public class EventSaveDto {

    private String title;
    private String description;
    private String locality;
    private Long userAppId;
    private Boolean status;
    private List<DateEventSaveDto> eventDates;
    private Long categoryId;
    private Long stateId;
    private Long cityId;
    private List<Long> musicalGenres;

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

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public Long getUserAppId() {
        return userAppId;
    }

    public void setUserAppId(Long userAppId) {
        this.userAppId = userAppId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<DateEventSaveDto> getEventDates() {
        return eventDates;
    }

    public void setEventDates(List<DateEventSaveDto> eventDates) {
        this.eventDates = eventDates;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public List<Long> getMusicalGenres() {
        return musicalGenres;
    }

    public void setMusicalGenres(List<Long> musicalGenres) {
        this.musicalGenres = musicalGenres;
    }
}
