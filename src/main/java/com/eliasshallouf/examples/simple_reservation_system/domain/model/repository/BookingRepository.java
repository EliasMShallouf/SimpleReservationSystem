package com.eliasshallouf.examples.simple_reservation_system.domain.model.repository;

import com.eliasshallouf.examples.simple_reservation_system.domain.model.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface BookingRepository extends CrudRepository<Book, String> {
    @Query(
        "SELECT (count(*) = 0) FROM Book b " +
        "where b.place.id = :place_id AND b.day = :date AND " +
        " (" +
        "        (:time_from BETWEEN b.from AND b.to) " +
        "        OR (:time_to BETWEEN b.from AND b.to) " +
        "        OR (b.from BETWEEN :time_from AND :time_to) " +
        "        OR (b.to BETWEEN :time_from AND :time_to) " +
        "    )"
    )
    Boolean isAvailable(String place_id, Date date, Time time_from, Time time_to);

    @Query("DELETE from Book b where b.id = :id AND b.user.id = :user_id")
    void unBook(String user_id, String id);

    @Query("select b from Book b where b.user.id = :user_id order by b.day desc, b.from desc")
    List<Book> getUserReservations(String user_id);

    @Query("select b from Book b order by b.day desc, b.from desc")
    List<Book> getAllReservations();
}

