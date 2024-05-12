package com.eliasshallouf.examples.simple_reservation_system.domain.model.repository;

import com.eliasshallouf.examples.simple_reservation_system.domain.model.Block;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface BlockRepository extends CrudRepository<Block, String> {
    @Query(
        "SELECT (count (*) != 0) FROM Block b " +
        "where b.place.id = :place_id AND b.day = :date AND " +
        " (" +
        "        (:time_from BETWEEN b.from AND b.to) " +
        "        OR (:time_to BETWEEN b.from AND b.to) " +
        "        OR (b.from BETWEEN :time_from AND :time_to) " +
        "        OR (b.to BETWEEN :time_from AND :time_to) " +
        "    )"
    )
    Boolean isBlocked(String place_id, Date date, Time time_from, Time time_to);

    @Query("select b from Block b where b.user.id = :user_id order by b.day desc, b.from desc")
    List<Block> getUserBlocks(String user_id);

    @Query("DELETE from Block b where b.id = :id AND b.user.id = :user_id ")
    void unBlock(String user_id, String id);

    @Query("SELECT b from Block b where b.place.id = :place_id ORDER BY b.day, b.from")
    List<Block> getPlaceBlockList(String place_id);
}
