package com.restaurant.service;

import com.restaurant.model.Reservation;
import com.restaurant.model.Table;
import com.restaurant.repository.ReservationRepository;
import com.restaurant.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TableRepository tableRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public Reservation findByIdreserv(String idreserv) {
        Reservation reservation = reservationRepository.findByIdreserv(idreserv);
        if (reservation != null) {
            // Force le chargement de la table
            reservation.getTable().getDesignation();
        }
        return reservation;
    }

    public Reservation createReservation(Reservation reservation) {
        // Check if table is available
        List<Reservation> existingReservations = reservationRepository
            .findByTableIdtableAndDateReservBetween(
                reservation.getTable().getIdtable(),
                reservation.getDateReserv(),
                reservation.getDateReserve()
            );

        if (!existingReservations.isEmpty()) {
            throw new RuntimeException("Table already reserved for this time period");
        }

        // Update table status
        Table table = reservation.getTable();
        table.setOccupation(true);
        tableRepository.save(table);

        return reservationRepository.save(reservation);
    }

    public Reservation updateReservation(Long id, Reservation reservationDetails) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isPresent()) {
            Reservation existingReservation = reservation.get();
            
            // Récupérer la table depuis la base de données
            Table table = tableRepository.findById(reservationDetails.getTable().getId())
                .orElseThrow(() -> new RuntimeException("Table not found"));
            
            existingReservation.setTable(table);
            existingReservation.setDateReserv(reservationDetails.getDateReserv());
            existingReservation.setDateReserve(reservationDetails.getDateReserve());
            existingReservation.setNomcli(reservationDetails.getNomcli());
            return reservationRepository.save(existingReservation);
        }
        return null;
    }

    public void deleteReservation(Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isPresent()) {
            // Free up the table
            Table table = reservation.get().getTable();
            table.setOccupation(false);
            tableRepository.save(table);
            
            reservationRepository.deleteById(id);
        }
    }

    public List<Reservation> getReservationsBetweenDates(LocalDateTime start, LocalDateTime end) {
        return reservationRepository.findByDateReservBetween(start, end);
    }
} 