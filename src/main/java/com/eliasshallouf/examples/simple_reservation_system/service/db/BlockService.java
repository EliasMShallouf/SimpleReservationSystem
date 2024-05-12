package com.eliasshallouf.examples.simple_reservation_system.service.db;

import com.eliasshallouf.examples.simple_reservation_system.domain.model.Block;
import com.eliasshallouf.examples.simple_reservation_system.domain.model.repository.BlockRepository;
import com.eliasshallouf.examples.simple_reservation_system.domain.model.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Service
public class BlockService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BlockRepository blockRepository;

    public List<Block> getUserBlocks(String userId) {
        return blockRepository.getUserBlocks(userId);
    }

    public String update(String block_id, Date date, Time time_from, Time time_to) {
        Block block = blockRepository.findById(block_id).get();

        String place_id = block.getPlace().getId();

        Boolean isAvailable = bookingRepository.isAvailable(place_id, date, time_from, time_to);
        Boolean isBlocked = blockRepository.isBlocked(place_id, date, time_from, time_to);

        if (isBlocked || !isAvailable) {
            throw new RuntimeException("Place already has blocked or booked");
        }

        block.setDay(date);
        block.setFrom(time_from);
        block.setTo(time_to);

        blockRepository.save(block);

        return block.getId();
    }

    public void delete(String id) {
        blockRepository.deleteById(id);
    }
}