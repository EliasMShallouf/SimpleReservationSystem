package com.eliasshallouf.examples.simple_reservation_system.service.api;

import com.eliasshallouf.examples.simple_reservation_system.domain.model.request.BookingModel;
import com.eliasshallouf.examples.simple_reservation_system.domain.model.request.UpdateBookingModel;
import com.eliasshallouf.examples.simple_reservation_system.service.db.AdminService;
import com.eliasshallouf.examples.simple_reservation_system.service.db.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/block")
public class BlockingEndpointService {
    @Autowired
    private AdminService userService;

    @Autowired
    private BlockService blockService;

    @PostMapping
    public String block(@RequestBody BookingModel bookingModel) {
        return userService.block(
            bookingModel.getUserId(),
            bookingModel.getPlaceId(),
            bookingModel.getDate(),
            bookingModel.getFrom(),
            bookingModel.getTo()
        );
    }

    @PutMapping
    public String updateBlock(@RequestBody UpdateBookingModel bookingModel) {
        return blockService.update(
            bookingModel.getBookId(),
            bookingModel.getDate(),
            bookingModel.getFrom(),
            bookingModel.getTo()
        );
    }

    @DeleteMapping("/{blockId}")
    public void deleteBlock(@PathVariable String bookId) {
        blockService.delete(bookId);
    }
}
