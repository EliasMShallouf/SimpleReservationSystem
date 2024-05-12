package com.eliasshallouf.examples.simple_reservation_system.domain.model;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.Date;

@Entity
@Table
public class Book {
    @Id
    private String id;
    @OneToOne
    @JoinColumn(name="place_id")
    private Place place;
    @OneToOne
    @JoinColumn(name="user_id")
    private User user;
    @Column
    private Date day;
    @Column(name = "book_to")
    private Time to;
    @Column(name = "book_from")
    private Time from;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Place getPlace() {
        return place;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Time getTo() {
        return to;
    }

    public void setTo(Time to) {
        this.to = to;
    }

    public Time getFrom() {
        return from;
    }

    public void setFrom(Time from) {
        this.from = from;
    }
}
