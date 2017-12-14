package com.example.rajeevkumar.parkingsolution;

import java.io.Serializable;

/**
 * Created by Rajeev Kumar on 08-08-2016.
 */
public class BookingModel implements Serializable {
    int booking_id;
    String date;
    int basement;
    int slot;
    int isBooked;
    // boolean booked;


    public BookingModel(int booking_id, String date, int basement, int slot, int isBooked) {
        this.booking_id = booking_id;
        this.date = date;
        this.basement = basement;
        this.slot = slot;
        this.isBooked = isBooked;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getBasement() {
        return basement;
    }

    public void setBasement(int basement) {
        this.basement = basement;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getIsBooked() {
        return isBooked;
    }

    public void setIsBooked(int isBooked) {
        this.isBooked = isBooked;
    }

    @Override
    public String toString() {
        return "BookingModel{" +
                "booking_id='" + booking_id + '\'' +
                ", booking_date='" + date + '\'' +
                ", basement_id=" + basement +
                ", slot_no='" + slot + '\'' +
                ", booked='" + isBooked + '\'' +
                '}';
    }
}