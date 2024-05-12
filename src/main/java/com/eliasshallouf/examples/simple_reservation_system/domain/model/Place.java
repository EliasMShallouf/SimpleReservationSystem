package com.eliasshallouf.examples.simple_reservation_system.domain.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Place {
    @Id
    private String id;
    @Column
    private String name;
    @ManyToOne
    @JoinColumn(name="room_id")
    private Room room;
    @ManyToMany
    @JoinTable(
        name = "place_items",
        joinColumns = {
            @JoinColumn(name = "place_id")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "item_id")
        }
    )
    private Set<Item> itemList;

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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room_id) {
        this.room = room_id;
    }

    public void setItems(Set<Item> itemList) {
        this.itemList = itemList;
    }

    public Set<Item> getItems() {
        return itemList;
    }

    public void addItem(Item item) {
        if(this.itemList == null)
            this.itemList = new HashSet<>();

        this.itemList.add(item);
    }

    public void removeItem(String itemId) {
        if(this.itemList == null)
            return;

        this.itemList.removeIf(item -> item.getId().equals(itemId));
    }
}
