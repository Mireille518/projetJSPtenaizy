package com.restaurant.repository;

import com.restaurant.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {
    Commande findByIdcom(String idcom);
    
    List<Commande> findByDatecomBetween(Date startDate, Date endDate);
    
    @Query("SELECT c FROM Commande c WHERE c.nomcli = :clientId AND c.datecom BETWEEN :startDate AND :endDate")
    List<Commande> findClientOrdersBetweenDates(int clientId, Date startDate, Date endDate);
    
    @Query("SELECT SUM(c.menu.pu * c.quantite) FROM Commande c WHERE c.datecom BETWEEN :startDate AND :endDate")
    Double calculateTotalRevenueBetweenDates(Date startDate, Date endDate);
    
    @Query("SELECT MONTH(c.datecom) as month, SUM(c.menu.pu * c.quantite) as revenue " +
           "FROM Commande c " +
           "WHERE c.datecom >= :startDate " +
           "GROUP BY MONTH(c.datecom) " +
           "ORDER BY month")
    List<Object[]> getMonthlyRevenue(Date startDate);
} 