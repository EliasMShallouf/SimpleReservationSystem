package com.eliasshallouf.examples.simple_reservation_system.service.api;

import com.eliasshallouf.examples.simple_reservation_system.domain.model.Item;
import com.eliasshallouf.examples.simple_reservation_system.domain.model.Place;
import com.eliasshallouf.examples.simple_reservation_system.domain.model.Room;
import com.eliasshallouf.examples.simple_reservation_system.domain.model.repository.PlacesRepository;
import com.eliasshallouf.examples.simple_reservation_system.domain.model.repository.RoomRepository;
import com.eliasshallouf.examples.simple_reservation_system.domain.model.request.InsertPlaceModel;
import com.eliasshallouf.examples.simple_reservation_system.domain.model.repository.ItemsRepository;
import com.eliasshallouf.examples.simple_reservation_system.service.utils.ListHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/data/")
public class DataEndpointService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private PlacesRepository placesRepository;
    @Autowired
    private ItemsRepository itemsRepository;

    @GetMapping("/rooms/{roomId}/places")
    public List<Place> roomPlaces(@PathVariable String roomId) {
        return placesRepository.getRoomPlaces(roomId);
    }

    @GetMapping("/rooms")
    public List<Room> rooms() {
        return ListHelper.toList(roomRepository.findAll());
    }

    @PostMapping("/rooms")
    public String createRoom(@RequestBody Room room) {
        room.setId(UUID.randomUUID().toString());
        roomRepository.save(room);
        return room.getId();
    }

    @DeleteMapping("/rooms/{roomId}")
    public void deleteRoom(@PathVariable String roomId) {
        roomRepository.deleteById(roomId);
    }

    @PutMapping("/rooms")
    public void updateRoom(@RequestBody Room room) {
        roomRepository.save(room);
    }

    @GetMapping("/items")
    public List<Item> items() {
        return ListHelper.toList(itemsRepository.findAll());
    }

    @PostMapping("/items")
    public String createItem(@RequestBody Item item) {
        itemsRepository.save(item);
        return item.getId();
    }

    @DeleteMapping("/items/{itemId}")
    public void deleteItem(@PathVariable String itemId) {
        itemsRepository.deleteById(itemId);
    }

    @PutMapping("/items")
    public void updateItem(@RequestBody Item item) {
        itemsRepository.save(item);
    }

    @PutMapping("/places")
    public void updatePlace(@RequestBody InsertPlaceModel insertPlaceModel) {
        Place place = placesRepository.findById(insertPlaceModel.getId()).get();

        Set<Item> itemSet = insertPlaceModel
            .getItems()
            .stream()
            .map(id -> {
                Item tmp = new Item();
                tmp.setId(id);
                return tmp;
            })
            .collect(Collectors.toSet());

        place.setName(insertPlaceModel.getName());
        place.setItems(itemSet);

        placesRepository.save(place);
    }

    @PostMapping("/places")
    public void createPlace(@RequestBody InsertPlaceModel insertPlaceModel) {
        Room room = roomRepository.findById(insertPlaceModel.getRoom()).get();
        if(Objects.equals(
            room.getMaxPlacesCount(),
            placesRepository.getRoomCurrentPlacesCount(insertPlaceModel.getRoom())
        )) {
            throw new RuntimeException("Room " + room.getName() + " capacity has overflowed");
        }

        Place place = new Place();
        place.setId(UUID.randomUUID().toString());
        place.setRoom(room);

        Set<Item> itemSet = insertPlaceModel
                .getItems()
                .stream()
                .map(id -> {
                    Item tmp = new Item();
                    tmp.setId(id);
                    return tmp;
                })
                .collect(Collectors.toSet());

        place.setName(insertPlaceModel.getName());
        place.setItems(itemSet);

        placesRepository.save(place);
    }

    @DeleteMapping("/places/{placeId}")
    public void deletePlace(@PathVariable String placeId) {
        placesRepository.deleteById(placeId);
    }
}
