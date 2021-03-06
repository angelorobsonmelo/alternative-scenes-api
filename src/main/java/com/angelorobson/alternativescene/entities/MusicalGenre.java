package com.angelorobson.alternativescene.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
public class MusicalGenre implements Serializable {

    private static final long serialVersionUID = 74785535352906538L;

    private Long id;
    private String name;
    private List<Event> events;

    public MusicalGenre() {
    }

    public MusicalGenre(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(cascade = ALL, fetch = LAZY, mappedBy = "musicalGenres")
    @JsonIgnore
    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MusicalGenre that = (MusicalGenre) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(events, that.events);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, events);
    }

    @Override
    public String toString() {
        return "MusicalGenre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", events=" + events +
                '}';
    }
}
