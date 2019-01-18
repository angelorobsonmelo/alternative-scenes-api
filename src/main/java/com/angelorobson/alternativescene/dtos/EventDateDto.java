package com.angelorobson.alternativescene.dtos;

import java.time.LocalDate;

public class EventDateDto {

    private LocalDate date;
    private String hour;
    private Double priceDate;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public Double getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(Double priceDate) {
        this.priceDate = priceDate;
    }
}
