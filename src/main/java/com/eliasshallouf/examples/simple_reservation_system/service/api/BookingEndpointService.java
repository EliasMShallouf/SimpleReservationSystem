package com.eliasshallouf.examples.simple_reservation_system.service.api;

import com.eliasshallouf.examples.simple_reservation_system.domain.model.request.BookingModel;
import com.eliasshallouf.examples.simple_reservation_system.domain.model.request.UpdateBookingModel;
import com.eliasshallouf.examples.simple_reservation_system.service.db.BookingService;
import com.eliasshallouf.examples.simple_reservation_system.service.db.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
public class BookingEndpointService {
    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public String book(@RequestBody BookingModel bookingModel) {
        return userService.book(
            bookingModel.getUserId(),
            bookingModel.getPlaceId(),
            bookingModel.getDate(),
            bookingModel.getFrom(),
            bookingModel.getTo()
        );
    }

    @PutMapping
    public String updateBooking(@RequestBody UpdateBookingModel bookingModel) {
        return bookingService.update(
            bookingModel.getBookId(),
            bookingModel.getDate(),
            bookingModel.getFrom(),
            bookingModel.getTo()
        );
    }

    @DeleteMapping("/{bookId}")
    public void deleteBooking(@PathVariable String bookId) {
        bookingService.delete(bookId);
    }
}
