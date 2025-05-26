package com.restaurant.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String idcom;

    @ManyToOne
    @JoinColumn(name = "idplat", nullable = false)
    private Menu menu;

    @Column(nullable = false)
    private int nomcli;

    @Column(nullable = false)
    private String typecom;

    @ManyToOne
    @JoinColumn(name = "idtable")
    private Table table;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datecom;

    private int quantite;
} 