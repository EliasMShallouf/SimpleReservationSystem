package com.eliasshallouf.examples.simple_reservation_system.domain.model.repository;

import com.eliasshallouf.examples.simple_reservation_system.domain.model.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemsRepository extends CrudRepository<Item, String> {
}
