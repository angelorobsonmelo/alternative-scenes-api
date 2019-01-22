package com.angelorobson.alternativescene.dtos;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class EventSaveDto {

    private Optional<Long> id = Optional.empty();
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

    public Optional<Long> getId() {
        return id;
    }

    public void setId(Optional<Long> id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventSaveDto that = (EventSaveDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(locality, that.locality) &&
                Objects.equals(userAppId, that.userAppId) &&
                Objects.equals(status, that.status) &&
                Objects.equals(eventDates, that.eventDates) &&
                Objects.equals(categoryId, that.categoryId) &&
                Objects.equals(stateId, that.stateId) &&
                Objects.equals(cityId, that.cityId) &&
                Objects.equals(musicalGenres, that.musicalGenres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, locality, userAppId, status, eventDates, categoryId, stateId, cityId, musicalGenres);
    }
}
