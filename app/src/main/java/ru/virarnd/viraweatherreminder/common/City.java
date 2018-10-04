package ru.virarnd.viraweatherreminder.common;

public class City {
    private int id;
    private String name;
    private String country;

    public City(int cityId, String cityName) {
        this.id = cityId;
        this.name = cityName;
        this.country = "";
    }

    public City(int cityId, String cityName, String country) {
        this.id = cityId;
        this.name = cityName;
        this.country = country;
    }

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
