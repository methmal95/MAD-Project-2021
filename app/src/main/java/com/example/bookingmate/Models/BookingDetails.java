package com.example.bookingmate.Models;

public class BookingDetails {

    String id;
    String name;
    String phone;
    String purpose;
    String arrive;
    String destination;

    public BookingDetails() {
    }

    public BookingDetails(String id, String name, String phone, String purpose, String arrive, String destination) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.purpose = purpose;
        this.arrive = arrive;
        this.destination = destination;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
