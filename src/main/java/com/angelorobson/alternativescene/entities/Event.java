package com.angelorobson.alternativescene.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static java.time.LocalDate.now;

@Entity
public class Event implements Serializable {

    private static final long serialVersionUID = -3534895515963460853L;

    private Long id;
    private String imageUrl;
    private String imageThumbUrl;
    private Locality locality;
    private UserApp userApp;
    private Boolean status;
    private List<EventDate> eventDates;
    private Category category;
    private List<MusicalGenre> musicalGenres;
    private LocalDate registrationDate;
    private Favorite favorite;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Column(nullable = false)
    public String getImageThumbUrl() {
        return imageThumbUrl;
    }

    public void setImageThumbUrl(String imageThumbUrl) {
        this.imageThumbUrl = imageThumbUrl;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Locality getLocality() {
        return locality;
    }

    public void setLocality(Locality locality) {
        this.locality = locality;
    }

    @ManyToOne
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


    @OneToMany(mappedBy = "event", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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


    @ManyToMany
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

    @OneToOne(mappedBy = "event", fetch = FetchType.LAZY)
    @JsonIgnore
    public Favorite getFavorite() {
        return favorite;
    }

    public void setFavorite(Favorite favorite) {
        this.favorite = favorite;
    }

    @PrePersist
    public void prePersist() {
        this.registrationDate = now();
        this.status = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) &&
                Objects.equals(imageUrl, event.imageUrl) &&
                Objects.equals(imageThumbUrl, event.imageThumbUrl) &&
                Objects.equals(locality, event.locality) &&
                Objects.equals(userApp, event.userApp) &&
                Objects.equals(status, event.status) &&
                Objects.equals(eventDates, event.eventDates) &&
                Objects.equals(category, event.category) &&
                Objects.equals(musicalGenres, event.musicalGenres) &&
                Objects.equals(registrationDate, event.registrationDate) &&
                Objects.equals(favorite, event.favorite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, imageUrl, imageThumbUrl, locality, userApp, status, eventDates, category, musicalGenres, registrationDate, favorite);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageThumbUrl='" + imageThumbUrl + '\'' +
                ", locality=" + locality +
                ", userApp=" + userApp +
                ", status=" + status +
                ", eventDates=" + eventDates +
                ", category=" + category +
                ", musicalGenres=" + musicalGenres +
                ", registrationDate=" + registrationDate +
                ", favorite=" + favorite +
                '}';
    }
}
