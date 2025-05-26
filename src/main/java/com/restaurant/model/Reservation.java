package com.restaurant.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String idreserv;

    @ManyToOne
    @JoinColumn(name = "idtable", nullable = false)
    private Table table;

    @Column(nullable = false)
    private LocalDateTime dateReserv;

    @Column(nullable = false)
    private LocalDateTime dateReserve;

    @Column(nullable = false)
    private String nomcli;
} 