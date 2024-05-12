package com.eliasshallouf.examples.simple_reservation_system.domain.model;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.Date;

@Entity
@Table
public class Block {
    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name="place_id", referencedColumnName = "id")
    private Place place;
    @Column
    private Date day;
    @Column(name = "block_from")
    private Time from;
    @Column(name = "block_to")
    private Time to;

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

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Time getFrom() {
        return from;
    }

    public void setFrom(Time from) {
        this.from = from;
    }

    public Time getTo() {
        return to;
    }

    public void setTo(Time to) {
        this.to = to;
    }
}
