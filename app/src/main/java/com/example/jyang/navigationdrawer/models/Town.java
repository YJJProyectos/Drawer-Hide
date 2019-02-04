package com.example.jyang.navigationdrawer.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Town {

    private int id;
    private String name;
    private String color;
    private City city;
    @SerializedName("cities")
    private List<City> ciudades;

    public Town(int id, String name, String color){
        this.id = id;
        this.name = name;
        this.color = color;
    }
    public Town(){

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<City> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<City> ciudades) {
        this.ciudades = ciudades;
    }
}
