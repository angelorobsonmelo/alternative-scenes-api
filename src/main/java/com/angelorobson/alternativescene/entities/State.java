package com.angelorobson.alternativescene.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class State implements Serializable {

    private static final long serialVersionUID = 3246458288826274268L;

    private Long id;
    private String name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(id, state.id) &&
                Objects.equals(name, state.name) &&
                Objects.equals(uf, state.uf) &&
                Objects.equals(cities, state.cities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, uf, cities);
    }

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", uf='" + uf + '\'' +
                ", cities=" + cities +
                '}';
    }
}
