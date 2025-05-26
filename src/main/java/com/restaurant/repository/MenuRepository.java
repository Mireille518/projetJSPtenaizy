package com.restaurant.repository;

import com.restaurant.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    Menu findByIdplat(String idplat);
    
    @Query("SELECT m FROM Menu m WHERE m.nomplat LIKE %:keyword%")
    List<Menu> searchByKeyword(String keyword);
    
    @Query(value = "SELECT m.* FROM menu m JOIN commande c ON m.id = c.menu_id GROUP BY m.id ORDER BY COUNT(c.id) DESC LIMIT 10", nativeQuery = true)
    List<Menu> findTop10MostOrderedDishes();
} 