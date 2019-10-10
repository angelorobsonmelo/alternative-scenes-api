package com.angelorobson.alternativescene.dtos;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class EventSaveDto {

    private Optional<Long> id = Optional.empty();
    private String locality;
    private String address;
    private Long userAppId;
    private List<DateEventSaveDto> eventDates;
    private Long categoryId;
    private String cityName;
    private List<Long> musicalGenres;
    private String imageUrl;

    public Optional<Long> getId() {
        return id;
    }

    public void setId(Optional<Long> id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public List<Long> getMusicalGenres() {
        return musicalGenres;
    }

    public void setMusicalGenres(List<Long> musicalGenres) {
        this.musicalGenres = musicalGenres;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventSaveDto that = (EventSaveDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(locality, that.locality) &&
                Objects.equals(address, that.address) &&
                Objects.equals(userAppId, that.userAppId) &&
                Objects.equals(eventDates, that.eventDates) &&
                Objects.equals(categoryId, that.categoryId) &&
                Objects.equals(cityName, that.cityName) &&
                Objects.equals(musicalGenres, that.musicalGenres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, locality, address, userAppId, eventDates, categoryId, cityName, musicalGenres);
    }

    @Override
    public String toString() {
        return "EventSaveDto{" +
                "id=" + id +
                ", locality='" + locality + '\'' +
                ", address='" + address + '\'' +
                ", userAppId=" + userAppId +
                ", eventDates=" + eventDates +
                ", categoryId=" + categoryId +
                ", cityName=" + cityName +
                ", musicalGenres=" + musicalGenres +
                '}';
    }
}
