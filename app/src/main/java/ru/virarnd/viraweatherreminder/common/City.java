package ru.virarnd.viraweatherreminder.common;

import android.os.Parcel;
import android.os.Parcelable;

public class City implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.country);
    }

    protected City(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.country = in.readString();
    }

    public static final Parcelable.Creator<City> CREATOR = new Parcelable.Creator<City>() {
        @Override
        public City createFromParcel(Parcel source) {
            return new City(source);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };
}
