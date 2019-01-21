package com.angelorobson.alternativescene.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class City implements Serializable {

    private static final long serialVersionUID = -5754246207015712518L;

    private Long id;
    private String city;
    private State state;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", state=" + state +
                '}';
    }
}
