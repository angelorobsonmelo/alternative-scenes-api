package com.angelorobson.alternativescene.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import static javax.persistence.FetchType.LAZY;

@Entity
public class EventDate implements Serializable {

    private static final long serialVersionUID = 6898364585678450L;

    private Long id;
    private LocalDate date;
    private String eventHour;
    private Event event;
    private PriceDate priceDate;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public String getEventHour() {
        return eventHour;
    }

    public void setEventHour(String eventHour) {
        this.eventHour = eventHour;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "eventDate", fetch = LAZY)
    public PriceDate getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(PriceDate priceDate) {
        this.priceDate = priceDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventDate eventDate = (EventDate) o;
        return Objects.equals(id, eventDate.id) &&
                Objects.equals(date, eventDate.date) &&
                Objects.equals(eventHour, eventDate.eventHour) &&
                Objects.equals(event, eventDate.event) &&
                Objects.equals(priceDate, eventDate.priceDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, eventHour, event, priceDate);
    }

    @Override
    public String toString() {
        return "EventDate{" +
                "id=" + id +
                ", date=" + date +
                ", eventHour='" + eventHour + '\'' +
                ", event=" + event +
                ", priceDate=" + priceDate +
                '}';
    }
}

