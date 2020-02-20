package com.angelorobson.alternativescene.dtos;

public class UserDeviceSaveDto {

    private String deviceId;
    private Long userId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
