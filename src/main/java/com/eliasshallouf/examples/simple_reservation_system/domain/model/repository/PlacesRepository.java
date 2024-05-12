package com.eliasshallouf.examples.simple_reservation_system.domain.model.repository;

import com.eliasshallouf.examples.simple_reservation_system.domain.model.Place;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlacesRepository extends CrudRepository<Place, String> {
    @Query("select a from Place a where a.room.id = :room order by a.name")
    List<Place> getRoomPlaces(String room);

    @Query("select count(a) from Place a where a.room.id = :room")
    Integer getRoomCurrentPlacesCount(String room);
}
