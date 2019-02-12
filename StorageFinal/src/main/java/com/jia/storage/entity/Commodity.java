package com.jia.storage.entity;


import java.io.Serializable;

public class Commodity implements Serializable {
    private Long id;

    private String name;

    private String location;

    private Double price;

    public Commodity(Long id, String name, String location, Double price) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.price = price;
    }

    public Commodity() {
        super();
    }

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
        this.name = name == null ? null : name.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Commodity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", price=" + price +
                '}';
    }
}