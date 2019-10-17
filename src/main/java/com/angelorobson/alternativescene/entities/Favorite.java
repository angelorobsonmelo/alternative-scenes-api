package com.angelorobson.alternativescene.entities;



import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Favorite implements Serializable {

    private static final long serialVersionUID = -353462551596346088L;

    private Long id;
    private Event event;
    private UserApp userApp;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @ManyToOne
    public UserApp getUserApp() {
        return userApp;
    }

    public void setUserApp(UserApp userApp) {
        this.userApp = userApp;
    }
}
