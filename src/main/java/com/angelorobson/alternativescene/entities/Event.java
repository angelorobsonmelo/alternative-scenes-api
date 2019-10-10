package com.angelorobson.alternativescene.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import static java.time.LocalDate.now;

@Entity
public class Event implements Serializable {

    private static final long serialVersionUID = -3534895515963460853L;

    private Long id;
    private String photoUrl;
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


    @OneToMany(mappedBy = "event", orphanRemoval = true, fetch=FetchType.EAGER, cascade = CascadeType.ALL)
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

    @PrePersist
    public void prePersist() {
        this.registrationDate = now();
        this.status = false;
    }


}
