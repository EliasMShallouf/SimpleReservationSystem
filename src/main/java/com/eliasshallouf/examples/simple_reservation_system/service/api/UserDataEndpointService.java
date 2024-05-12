package com.eliasshallouf.examples.simple_reservation_system.service.api;

import com.eliasshallouf.examples.simple_reservation_system.domain.model.Block;
import com.eliasshallouf.examples.simple_reservation_system.domain.model.Book;
import com.eliasshallouf.examples.simple_reservation_system.service.db.BlockService;
import com.eliasshallouf.examples.simple_reservation_system.service.db.BookingService;
import com.eliasshallouf.examples.simple_reservation_system.service.db.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/users/")
public class UserDataEndpointService {
    @Autowired
    private UserService userService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private BlockService blockService;

    @GetMapping("/{userId}/reservations")
    public List<Book> userReservations(@PathVariable String userId) {
        if(userService.isAdmin(userId))
            return bookingService.getAllReservations();
        return bookingService.getUserReservations(userId);
    }

    @GetMapping("/{userId}/blocks")
    public List<Block> userBlocks(@PathVariable String userId) {
        return blockService.getUserBlocks(userId);
    }
}
