package ru.virarnd.viraweatherreminder.common;

import android.os.Parcel;
import android.os.Parcelable;

public class Forecast implements Parcelable {
    private final City city;
    private final int dayTemp;
    private final int nightTemp;
    private final int windDirection;            // Всего 8 направлений ветра
    private final int windSpeed;
    private final int humidity;
    private final double pressure;
    private final int weatherConditions;        // Всего 9 типов погодных условий

    // Weather Condition types:                 Wind Direction types:
    //      0 - clear sky,                              0 -
    //      1 - few clouds,                             1 -
    //      2 - scattered clouds,                       2 -
    //      3 - broken clouds                           3 -
    //      4 - shower rain                             4 -
    //      5 - rain                                    5 -
    //      6 - thunderstorm                            6 -
    //      7 - snow                                    7 -
    //      8 - mist



    public City getCity() {
        return city;
    }

    public int getDayTemp() {
        return dayTemp;
    }

    public int getNightTemp() {
        return nightTemp;
    }

    public int getWindDirection() {
        return windDirection;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public int getWeatherConditions() {
        return weatherConditions;
    }

    public static class Builder {
        private final City city;
        private int dayTemp;
        private int nightTemp;
        private int windDirection;
        private int windSpeed;
        private int humidity;
        private double pressure;
        private int weatherConditions;

        public Builder(int cityId, String cityName) {
            this.city = new City(cityId, cityName);
        }

        public Builder(int cityId, String cityName, String country) {
            this.city = new City(cityId, cityName, country);
        }


        public Builder dayTemp(int val) {
            dayTemp = val;
            return this;
        }

        public Builder nightTemp(int val) {
            nightTemp = val;
            return this;
        }

        public Builder windDirection(int val) {
            windDirection = val;
            return this;
        }

        public Builder windSpeed(int val) {
            windSpeed = val;
            return this;
        }

        public Builder humidity(int val) {
            humidity = val;
            return this;
        }

        public Builder pressure(double val) {
            pressure = val;
            return this;
        }

        public Builder weatherConditions(int val) {
            weatherConditions = val;
            return this;
        }

        public Forecast build() {
            return new Forecast(this);
        }

    }

    private Forecast(Builder builder) {
        this.city = builder.city;
        this.dayTemp = builder.dayTemp;
        this.nightTemp = builder.nightTemp;
        this.windDirection = builder.windDirection;
        this.windSpeed = builder.windSpeed;
        this.humidity = builder.humidity;
        this.pressure = builder.pressure;
        this.weatherConditions = builder.weatherConditions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.city, flags);
        dest.writeInt(this.dayTemp);
        dest.writeInt(this.nightTemp);
        dest.writeInt(this.windDirection);
        dest.writeInt(this.windSpeed);
        dest.writeInt(this.humidity);
        dest.writeDouble(this.pressure);
        dest.writeInt(this.weatherConditions);
    }

    private Forecast(Parcel in) {
        this.city = in.readParcelable(City.class.getClassLoader());
        this.dayTemp = in.readInt();
        this.nightTemp = in.readInt();
        this.windDirection = in.readInt();
        this.windSpeed = in.readInt();
        this.humidity = in.readInt();
        this.pressure = in.readDouble();
        this.weatherConditions = in.readInt();
    }

    public static final Parcelable.Creator<Forecast> CREATOR = new Parcelable.Creator<Forecast>() {
        @Override
        public Forecast createFromParcel(Parcel source) {
            return new Forecast(source);
        }

        @Override
        public Forecast[] newArray(int size) {
            return new Forecast[size];
        }
    };
}
