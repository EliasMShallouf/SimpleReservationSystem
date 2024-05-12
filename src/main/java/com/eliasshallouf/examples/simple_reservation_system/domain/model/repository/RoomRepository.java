package com.eliasshallouf.examples.simple_reservation_system.domain.model.repository;

import com.eliasshallouf.examples.simple_reservation_system.domain.model.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, String> {
}
