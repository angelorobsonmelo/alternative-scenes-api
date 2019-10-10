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
    private String address;
    private Double latitude;
    private Double Longitude;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @ManyToOne(fetch = EAGER)
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Locality locality = (Locality) o;
        return Objects.equals(id, locality.id) &&
                Objects.equals(name, locality.name) &&
                Objects.equals(address, locality.address) &&
                Objects.equals(latitude, locality.latitude) &&
                Objects.equals(Longitude, locality.Longitude) &&
                Objects.equals(city, locality.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, latitude, Longitude, city);
    }

    @Override
    public String toString() {
        return "Locality{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", latitude=" + latitude +
                ", Longitude=" + Longitude +
                ", city=" + city +
                '}';
    }
}
