package com.angelorobson.alternativescene.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class PriceDate implements Serializable {

    private static final long serialVersionUID = -2183766102569990909L;

    private Long id;
    private Double price;
    private EventDate eventDate;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @JsonIgnore
    @OneToOne
    public EventDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(EventDate eventDate) {
        this.eventDate = eventDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceDate that = (PriceDate) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(price, that.price) &&
                Objects.equals(eventDate, that.eventDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, eventDate);
    }

    @Override
    public String toString() {
        return "PriceDate{" +
                "id=" + id +
                ", event=" +
                ", price=" + price +
                ", eventDate=" + eventDate +
                '}';
    }
}
