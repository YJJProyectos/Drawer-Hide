package com.example.jyang.navigationdrawer.model;

public class Car {
    private int VIN;
    private String name;
    private String color;

    public  Car(int VIN, String name, String color){
        this.VIN = VIN;
        this.name = name;
        this.color = color;
    }

    public void setVIN(int VIN) {
        this.VIN = VIN;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getVIN() {
        return VIN;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}
