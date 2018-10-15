package ru.virarnd.viraweatherreminder.common;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

public class Notification implements Parcelable {
    // Класс "Уведомления": название события, знает город, время планируемого события, время предварительной
    // проверки (ДО события, когда нужно проверить погоду и выкинуть уведомление юзеру)
    // Событие считается одинаковым, если совпадают название события, город и дата (переопределён метод equals)

    private String eventName;
    private City city;
    private GregorianCalendar eventDate;
    private GregorianCalendar checkDate;

    public Notification(String eventName, City city, GregorianCalendar eventDate, GregorianCalendar checkDate) {
        this.eventName = eventName;
        this.city = city;
        this.eventDate = eventDate;
        this.checkDate = checkDate;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Calendar getEventDate() {
        return eventDate;
    }

    public void setEventDate(GregorianCalendar eventDate) {
        this.eventDate = eventDate;
    }

    public Calendar getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(GregorianCalendar checkDate) {
        this.checkDate = checkDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Notification)) {
            return false;
        }
        Notification notification = (Notification) obj;
        return Objects.equals(eventName, notification.eventName) &&
                Objects.equals(city, notification.city) &&
                Objects.equals(eventDate, notification.eventDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventName, city, eventDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.eventName);
        dest.writeParcelable(this.city, flags);
        dest.writeSerializable(this.eventDate);
        dest.writeSerializable(this.checkDate);
    }

    protected Notification(Parcel in) {
        this.eventName = in.readString();
        this.city = in.readParcelable(City.class.getClassLoader());
        this.eventDate = (GregorianCalendar) in.readSerializable();
        this.checkDate = (GregorianCalendar) in.readSerializable();
    }

    public static final Parcelable.Creator<Notification> CREATOR = new Parcelable.Creator<Notification>() {
        @Override
        public Notification createFromParcel(Parcel source) {
            return new Notification(source);
        }

        @Override
        public Notification[] newArray(int size) {
            return new Notification[size];
        }
    };
}
