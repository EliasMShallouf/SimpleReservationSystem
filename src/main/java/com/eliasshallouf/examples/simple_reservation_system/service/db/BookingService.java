package com.eliasshallouf.examples.simple_reservation_system.service.db;

import com.eliasshallouf.examples.simple_reservation_system.domain.model.Book;
import com.eliasshallouf.examples.simple_reservation_system.domain.model.repository.BlockRepository;
import com.eliasshallouf.examples.simple_reservation_system.domain.model.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BlockRepository blockrepository;

    public String update(String book_id, Date date, Time time_from, Time time_to) {
        Book book = bookingRepository.findById(book_id).get();

        String place_id = book.getPlace().getId();

        Boolean isAvailable = bookingRepository.isAvailable(place_id, date, time_from, time_to);
        Boolean isBlocked = blockrepository.isBlocked(place_id, date, time_from, time_to);

        if (isBlocked || !isAvailable) {
            throw new RuntimeException("Place already has blocked or booked");
        }

        book.setDay(date);
        book.setFrom(time_from);
        book.setTo(time_to);

        bookingRepository.save(book);

        return book.getId();
    }

    public void delete(String id) {
        bookingRepository.deleteById(id);
    }

    public List<Book> getAllReservations() {
        return bookingRepository.getAllReservations();
    }

    public List<Book> getUserReservations(String userId) {
        return bookingRepository.getUserReservations(userId);
    }
}