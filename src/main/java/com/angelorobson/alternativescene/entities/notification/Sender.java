package com.angelorobson.alternativescene.entities.notification;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Sender<T> {

    @SerializedName("registration_ids")
    private List<String> registrationIds;
    private String to;
    private T data;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<String> getRegistrationIds() {
        return registrationIds;
    }

    public void setRegistrationIds(List<String> registrationIds) {
        this.registrationIds = registrationIds;
    }
}
