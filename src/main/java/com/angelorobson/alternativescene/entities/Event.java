package com.angelorobson.alternativescene.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static java.time.LocalDate.now;
import static javax.persistence.FetchType.EAGER;

@Entity
public class Event implements Serializable {

    private static final long serialVersionUID = -3534895515963460853L;

    private Long id;
    private String photoUrl;
    private String title;
    private String description;
    private Locality locality;
    private UserApp userApp;
    private Boolean status;
    private List<EventDate> eventDates;
    private Category category;
    private List<MusicalGenre> musicalGenres;
    private LocalDate registrationDate;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(fetch = EAGER)
    public Locality getLocality() {
        return locality;
    }

    public void setLocality(Locality locality) {
        this.locality = locality;
    }

    @ManyToOne(fetch = EAGER)
    public UserApp getUserApp() {
        return userApp;
    }

    public void setUserApp(UserApp userApp) {
        this.userApp = userApp;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


    @OneToMany(mappedBy = "event")
    public List<EventDate> getEventDates() {
        return eventDates;
    }

    public void setEventDates(List<EventDate> eventDates) {
        this.eventDates = eventDates;
    }

    @ManyToOne
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @ManyToMany(fetch = EAGER)
    @JoinTable(name = "musical_genre_event",
            joinColumns = {@JoinColumn(name = "event_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "musical_genre_id", nullable = false)})
    public List<MusicalGenre> getMusicalGenres() {
        return musicalGenres;
    }

    public void setMusicalGenres(List<MusicalGenre> musicalGenres) {
        this.musicalGenres = musicalGenres;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    @PrePersist
    public void prePersist() {
        final LocalDate now = now();
        this.registrationDate = now;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) &&
                Objects.equals(photoUrl, event.photoUrl) &&
                Objects.equals(title, event.title) &&
                Objects.equals(description, event.description) &&
                Objects.equals(locality, event.locality) &&
                Objects.equals(userApp, event.userApp) &&
                Objects.equals(status, event.status) &&
                Objects.equals(eventDates, event.eventDates) &&
                Objects.equals(category, event.category) &&
                Objects.equals(musicalGenres, event.musicalGenres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, photoUrl, title, description, locality, userApp, status, eventDates, category, musicalGenres);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", photoUrl='" + photoUrl + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", locality=" + locality +
                ", userApp=" + userApp +
                ", status=" + status +
                ", eventDates=" + eventDates +
                ", category=" + category +
                ", musicalGenres=" + musicalGenres +
                '}';
    }
}
