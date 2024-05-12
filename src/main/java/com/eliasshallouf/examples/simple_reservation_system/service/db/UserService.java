package com.eliasshallouf.examples.simple_reservation_system.service.db;

import com.eliasshallouf.examples.simple_reservation_system.domain.model.Book;
import com.eliasshallouf.examples.simple_reservation_system.domain.model.Place;
import com.eliasshallouf.examples.simple_reservation_system.domain.model.User;
import com.eliasshallouf.examples.simple_reservation_system.domain.model.exceptions.LoginException;
import com.eliasshallouf.examples.simple_reservation_system.domain.model.repository.BlockRepository;
import com.eliasshallouf.examples.simple_reservation_system.domain.model.repository.BookingRepository;
import com.eliasshallouf.examples.simple_reservation_system.domain.model.repository.PlacesRepository;
import com.eliasshallouf.examples.simple_reservation_system.domain.model.repository.UserRepository;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlacesRepository placesRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BlockRepository blockrepository;

    public boolean isAdmin(String userId) {
        return userRepository.isAdmin(userId);
    }

    public User getUser(String userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User register(User user) {
        if (userRepository.findByEmail(user.getGithubEmail()) != null)
            throw new LoginException("User with similar email already exists");

        // TODO Validation
        user.setId(UUID.randomUUID().toString());

        userRepository.save(user);

        return user;
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new LoginException("User not found with this email");
        }

        if(!user.getPassword().equals(password)) {
            throw new LoginException("Password Incorrect");
        }

        return user;
    }

    public String book(String user_id, String place_id, Date date, Time time_from, Time time_to) {
        Boolean isAvailable = bookingRepository.isAvailable(place_id, date, time_from, time_to);
        Boolean isBlocked = blockrepository.isBlocked(place_id, date, time_from, time_to);

        if (isBlocked || !isAvailable) {
            throw new RuntimeException("Place already has blocked or booked");
        }

        User user = new User();
        user.setId(user_id);

        Place place = new Place();
        place.setId(place_id);

        Book book = new Book();
        book.setUser(user);
        book.setPlace(place);
        book.setDay(date);
        book.setFrom(time_from);
        book.setTo(time_to);
        book.setId(UUID.randomUUID().toString());

        bookingRepository.save(book);

        return book.getId();
    }

    public void unBook(String user_id, String book_id){
        bookingRepository.unBook(user_id, book_id);
    }
}