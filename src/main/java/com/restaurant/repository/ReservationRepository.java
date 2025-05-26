package com.restaurant.repository;

import com.restaurant.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Reservation findByIdreserv(String idreserv);
    
    List<Reservation> findByDateReservBetween(LocalDateTime start, LocalDateTime end);
    
    List<Reservation> findByTableIdtableAndDateReservBetween(
        String idtable, 
        LocalDateTime start, 
        LocalDateTime end
    );
} 