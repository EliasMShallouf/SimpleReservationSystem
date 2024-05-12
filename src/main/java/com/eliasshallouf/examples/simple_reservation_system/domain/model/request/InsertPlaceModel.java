package com.eliasshallouf.examples.simple_reservation_system.domain.model.request;

import java.io.Serializable;
import java.util.List;

public class InsertPlaceModel implements Serializable {
    private String id;
    private String name;
    private String room;
    private List<String> items;

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

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}
