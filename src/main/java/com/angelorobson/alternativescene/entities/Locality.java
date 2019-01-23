package com.angelorobson.alternativescene.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import static javax.persistence.FetchType.EAGER;

@Entity
public class Locality implements Serializable {

    private static final long serialVersionUID = 2578667415370291292L;

    private Long id;
    private String name;
    private City city;

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

    @ManyToOne(fetch = EAGER)
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Locality locality1 = (Locality) o;
        return Objects.equals(id, locality1.id) &&
                Objects.equals(name, locality1.name) &&
                Objects.equals(city, locality1.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, city);
    }

    @Override
    public String toString() {
        return "Locality{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city=" + city +
                '}';
    }
}
