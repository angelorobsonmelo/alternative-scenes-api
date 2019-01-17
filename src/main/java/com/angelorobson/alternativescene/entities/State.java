package com.angelorobson.alternativescene.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class State implements Serializable {

    private static final long serialVersionUID = 3246458288826274268L;

    private Long id;
    private String state;
    private String uf;
    private List<City> cities;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }


    @OneToMany(mappedBy = "state", fetch = FetchType.LAZY)
    @JsonIgnore
    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", state='" + state + '\'' +
                ", uf='" + uf + '\'' +
                ", cities=" + cities +
                '}';
    }
}
