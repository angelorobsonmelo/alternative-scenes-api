package com.angelorobson.alternativescene.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class EventDate implements Serializable {

    private static final long serialVersionUID = 6898364585678450L;

    private Long id;
    private LocalDate eventDate;
    private String eventHour;
    private Event event;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @ManyToOne
    @JsonIgnore
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventDate eventDate1 = (EventDate) o;
        return Objects.equals(id, eventDate1.id) &&
                Objects.equals(eventDate, eventDate1.eventDate) &&
                Objects.equals(eventHour, eventDate1.eventHour) &&
                Objects.equals(event, eventDate1.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventDate, eventHour, event);
    }

    @Override
    public String toString() {
        return "EventDate{" +
                "id=" + id +
                ", eventDate=" + eventDate +
                ", eventHour='" + eventHour + '\'' +
                ", event=" + event +
                '}';
    }
}
