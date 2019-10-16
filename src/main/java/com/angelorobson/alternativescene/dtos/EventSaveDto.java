package com.angelorobson.alternativescene.dtos;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class EventSaveDto {

    private Optional<Long> id = Optional.empty();
    private String locality;
    private String address;
    private Double longitude;
    private Double latitude;
    private Long userAppId;
    private List<DateEventSaveDto> eventDates;
    private String cityName;
    private String imageUrl;
    private String imageThumbUrl;

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

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getImageThumbUrl() {
        return imageThumbUrl;
    }

    public void setImageThumbUrl(String imageThumbUrl) {
        this.imageThumbUrl = imageThumbUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventSaveDto that = (EventSaveDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(locality, that.locality) &&
                Objects.equals(address, that.address) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(userAppId, that.userAppId) &&
                Objects.equals(eventDates, that.eventDates) &&
                Objects.equals(cityName, that.cityName) &&
                Objects.equals(imageUrl, that.imageUrl) &&
                Objects.equals(imageThumbUrl, that.imageThumbUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, locality, address, longitude, latitude, userAppId, eventDates, cityName, imageUrl, imageThumbUrl);
    }

    @Override
    public String toString() {
        return "EventSaveDto{" +
                "id=" + id +
                ", locality='" + locality + '\'' +
                ", address='" + address + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", userAppId=" + userAppId +
                ", eventDates=" + eventDates +
                ", cityName='" + cityName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageThumbUrl='" + imageThumbUrl + '\'' +
                '}';
    }
}
