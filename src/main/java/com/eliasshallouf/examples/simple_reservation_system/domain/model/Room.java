package com.eliasshallouf.examples.simple_reservation_system.domain.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table
public class Room implements Serializable {
    @Id
    private String id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private Integer maxPlacesCount;

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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setMaxPlacesCount(Integer maxPlacesCount) {
        this.maxPlacesCount = maxPlacesCount;
    }

    public Integer getMaxPlacesCount() {
        return maxPlacesCount;
    }
}

