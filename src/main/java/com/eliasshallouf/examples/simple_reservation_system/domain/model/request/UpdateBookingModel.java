package com.eliasshallouf.examples.simple_reservation_system.domain.model.request;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class UpdateBookingModel implements Serializable {
    private String bookId;
    private Date day;
    private Time from;
    private Time to;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Date getDate() {
        return day;
    }

    public void setDate(Date date) {
        this.day = date;
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
