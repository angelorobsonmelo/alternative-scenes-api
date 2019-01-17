package com.angelorobson.alternativescene.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
public class Category implements Serializable {

    private static final long serialVersionUID = -8600634840157533556L;

    private Long id;
    private String category;
    private List<Event> events;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
