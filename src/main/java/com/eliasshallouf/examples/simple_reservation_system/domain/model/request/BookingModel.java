package com.eliasshallouf.examples.simple_reservation_system.domain.model.request;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class BookingModel implements Serializable {
    private String userId;
    private String placeId;
    private Date date;
    private Time from;
    private Time to;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
