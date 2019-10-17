package com.angelorobson.alternativescene.dtos;

public class FavoriteSaveDto {

    private Long userAppId;
    private Long eventId;

    public Long getUserAppId() {
        return userAppId;
    }

    public void setUserAppId(Long userAppId) {
        this.userAppId = userAppId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
