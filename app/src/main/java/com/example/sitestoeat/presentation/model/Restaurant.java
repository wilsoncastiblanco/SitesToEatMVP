package com.example.sitestoeat.presentation.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wilsoncastiblanco on 8/6/16.
 */
public class Restaurant {

    private int id;
    private String name;
    @SerializedName("especialidad")
    private String speciality;
    private double longitude;
    private double latitude;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
