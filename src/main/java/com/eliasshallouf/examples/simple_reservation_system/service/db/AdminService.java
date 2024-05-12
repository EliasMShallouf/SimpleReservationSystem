package com.eliasshallouf.examples.simple_reservation_system.service.db;

import com.eliasshallouf.examples.simple_reservation_system.domain.model.Block;
import com.eliasshallouf.examples.simple_reservation_system.domain.model.Item;
import com.eliasshallouf.examples.simple_reservation_system.domain.model.Place;
import com.eliasshallouf.examples.simple_reservation_system.domain.model.User;
import com.eliasshallouf.examples.simple_reservation_system.domain.model.repository.BlockRepository;
import com.eliasshallouf.examples.simple_reservation_system.domain.model.repository.BookingRepository;
import com.eliasshallouf.examples.simple_reservation_system.domain.model.repository.ItemsRepository;
import com.eliasshallouf.examples.simple_reservation_system.domain.model.repository.PlacesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AdminService {
    @Autowired
    private PlacesRepository placesRepository;
    @Autowired
    private ItemsRepository itemsRepository;
    @Autowired
    private BlockRepository blockRepository;
    @Autowired
    private BookingRepository bookingRepository;

    public void addItem(Item item) {
        if (itemsRepository.findById(item.getId()).isPresent())
            return;

        itemsRepository.save(item);
    }

    public String block(String user_id, String place_id, Date date, Time time_from, Time time_to) {
        Boolean isAvailable = bookingRepository.isAvailable(place_id, date, time_from, time_to);
        Boolean isBlocked = blockRepository.isBlocked(place_id, date, time_from, time_to);

        if (isBlocked || !isAvailable) {
            throw new RuntimeException("Place already blocked or booked");
        }

        User user = new User();
        user.setId(user_id);

        Place place = new Place();
        place.setId(place_id);

        Block block = new Block();
        block.setUser(user);
        block.setPlace(place);
        block.setDay(date);
        block.setFrom(time_from);
        block.setTo(time_to);
        block.setId(UUID.randomUUID().toString());

        blockRepository.save(block);

        return block.getId();
    }

    public void unBlock(String user_id, String block_id) {
        blockRepository.unBlock(user_id, block_id);
    }

    public List<Block> getPlaceBlockList(String place_id) {
        return blockRepository.getPlaceBlockList(place_id);
    }
}
