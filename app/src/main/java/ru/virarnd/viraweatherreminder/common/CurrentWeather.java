package ru.virarnd.viraweatherreminder.common;

import android.os.Parcel;
import android.os.Parcelable;

public class CurrentWeather implements Parcelable {
    private final City city;
    private final int weatherId;
    private final String main;
    private final String description;
    private final String icon;
    private final int nowTemp;
    private final double pressure;
    private final int humidity;
    private final int windDirection;            // Всего 8 направлений ветра
    private final int windSpeed;
    private final int weatherConditions;        // Всего 9 типов погодных условий
    private final int cloudiness;               // Параметр облачности


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

    public int getNowTemp() {
        return nowTemp;
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

    public int getWeatherId() {
        return weatherId;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public int getCloudiness() {
        return cloudiness;
    }

    public static class Builder {
        private final City city;
        private int nowTemp;
        private int windDirection;
        private int windSpeed;
        private int humidity;
        private double pressure;
        private int weatherConditions;
        private int cloudiness;
        private int weatherId;
        private String main;
        private String description;
        private String icon;

        public Builder(int cityId, String cityName) {
            this.city = new City(cityId, cityName);
        }

        public Builder(int cityId, String cityName, String country) {
            this.city = new City(cityId, cityName, country);
        }


        public Builder nowTemp(int val) {
            nowTemp = val;
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

        public Builder cloudiness(int val) {
            cloudiness = val;
            return this;
        }

        public Builder weatherId(int val) {
            weatherId = val;
            return this;
        }

        public Builder main(String val) {
            main = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder icon(String val) {
            icon = val;
            return this;
        }

        public CurrentWeather build() {
            return new CurrentWeather(this);
        }

    }

    private CurrentWeather(Builder builder) {
        this.city = builder.city;
        this.nowTemp = builder.nowTemp;
        this.windDirection = builder.windDirection;
        this.windSpeed = builder.windSpeed;
        this.humidity = builder.humidity;
        this.pressure = builder.pressure;
        this.weatherConditions = builder.weatherConditions;
        this.cloudiness = builder.cloudiness;
        this.weatherId = builder.weatherId;
        this.main = builder.main;
        this.description = builder.description;
        this.icon = builder.icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.city, flags);
        dest.writeInt(this.nowTemp);
        dest.writeInt(this.windDirection);
        dest.writeInt(this.windSpeed);
        dest.writeInt(this.humidity);
        dest.writeDouble(this.pressure);
        dest.writeInt(this.weatherConditions);
        dest.writeInt(this.cloudiness);
        dest.writeInt(this.weatherId);
        dest.writeString(this.main);
        dest.writeString(this.description);
        dest.writeString(this.icon);
    }

    private CurrentWeather(Parcel in) {
        this.city = in.readParcelable(City.class.getClassLoader());
        this.nowTemp = in.readInt();
        this.windDirection = in.readInt();
        this.windSpeed = in.readInt();
        this.humidity = in.readInt();
        this.pressure = in.readDouble();
        this.weatherConditions = in.readInt();
        this.cloudiness = in.readInt();
        this.weatherId = in.readInt();
        this.main = in.readString();
        this.description = in.readString();
        this.icon = in.readString();
    }

    public static final Parcelable.Creator<CurrentWeather> CREATOR = new Parcelable.Creator<CurrentWeather>() {
        @Override
        public CurrentWeather createFromParcel(Parcel source) {
            return new CurrentWeather(source);
        }

        @Override
        public CurrentWeather[] newArray(int size) {
            return new CurrentWeather[size];
        }
    };
}
