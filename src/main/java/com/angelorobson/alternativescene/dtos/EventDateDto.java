package com.angelorobson.alternativescene.dtos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static java.time.format.DateTimeFormatter.*;

public class EventDateDto {

    private Long id;
    private LocalDate date;
    private String hour;
    private Double price;
    private String eventDateAndHourToString;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setEventDateAndHourToString() {
        DateTimeFormatter formatter = ofPattern("d 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));
        String formattedString = date.format(formatter);

        eventDateAndHourToString = formattedString + ", " + hour + "h";
    }

    public String getEventDateAndHourToString() {
        return eventDateAndHourToString;
    }

}