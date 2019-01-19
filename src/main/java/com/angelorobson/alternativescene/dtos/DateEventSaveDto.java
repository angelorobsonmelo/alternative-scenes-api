package com.angelorobson.alternativescene.dtos;

import java.time.LocalDate;

public class DateEventSaveDto {

    private LocalDate eventDate;
    private String eventHour;
    private Double priceDate;

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventHour() {
        return eventHour;
    }

    public void setEventHour(String eventHour) {
        this.eventHour = eventHour;
    }

    public Double getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(Double priceDate) {
        this.priceDate = priceDate;
    }
}
