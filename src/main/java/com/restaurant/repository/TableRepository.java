package com.restaurant.repository;

import com.restaurant.model.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepository extends JpaRepository<Table, Long> {
    Table findByIdtable(String idtable);
} 